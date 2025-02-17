package com.example.simplesns.domain.user.service;

import com.example.simplesns.common.config.PasswordEncoder;
import com.example.simplesns.domain.user.dto.request.LoginRequestDto;
import com.example.simplesns.domain.user.dto.request.UserDeleteRequestDto;
import com.example.simplesns.domain.user.dto.request.UserProfileRequestDto;
import com.example.simplesns.domain.user.dto.request.UserSaveRequestDto;
import com.example.simplesns.domain.user.dto.response.UserProfileResponseDto;
import com.example.simplesns.domain.user.dto.response.UserResponseDto;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 생성(회원가입)
    @Transactional
    public UserResponseDto sava(UserSaveRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 이메일은 이미 사용중입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getEmail(), dto.getName(), dto.getBirthdate(), encodedPassword);
        userRepository.save(user);

        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getBirthdate(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // 전체 유저 목록 조회
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();

        List<UserResponseDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(new UserResponseDto(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getBirthdate(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()));
        }

        return dtos;
    }

    // 특정 유저 프로필 조회
    @Transactional
    public UserProfileResponseDto findOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 존재하지 않습니다."));

        return new UserProfileResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getBirthdate(),
                user.getImage(),
                user.getMbti()
        );
    }

    // 특정 유저 프로필 수정(이메일 수정 불가, 비밀번호 확인 필요)
    @Transactional
    public UserProfileResponseDto updateProfile(Long userId, UserProfileRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 존재하지 않습니다."));

        if (isPasswordNotMatched(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        user.updateProfile(
                dto.getName(),
                dto.getBirthdate(),
                dto.getImage(),
                dto.getMbti()
        );

        return new UserProfileResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getBirthdate(),
                user.getImage(),
                user.getMbti());
    }

    // 유저 삭제(회원탈퇴. 이메일&비밀번호 확인 필요)
    @Transactional
    public void delete(Long userId, UserDeleteRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 존재하지 않습니다."));

        if (!dto.getEmail().equals(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이메일이 일치하지 않습니다.");
        }

        if (isPasswordNotMatched(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public Long handleLogin(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 이메일이 존재하지 않습니다."));

        if (isPasswordNotMatched(dto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return user.getId();
    }

    private boolean isPasswordNotMatched(String inputPassword, String encodedPassword) {
        return !passwordEncoder.matches(inputPassword, encodedPassword);
    }
}