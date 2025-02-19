package com.example.simplesns.domain.user.service;

import com.example.simplesns.common.config.PasswordEncoder;
import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.user.dto.request.*;
import com.example.simplesns.domain.user.dto.response.UserProfileResponseDto;
import com.example.simplesns.domain.user.dto.response.UserResponseDto;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public PaginationResponse<UserResponseDto> findAll(int page, int size) {

        PageRequest pageable = createPageable(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        return new PaginationResponse<>(usersPage.map(UserResponseDto::new));
    }

    // 특정 유저 프로필 조회
    @Transactional
    public UserProfileResponseDto findOne(Long userId) {
        User user = getUser(userId);

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
        User user = getUser(userId);

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

    // 특정 유저 비밀번호 수정
    @Transactional
    public void updatePassword(Long userId, UserPasswordUpdateRequestDto dto) {
        User user = getUser(userId);

        if (isPasswordNotMatched(dto.getCurrentPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존 비밀번호가 일치하지 않습니다.");
        }

        if (!isPasswordNotMatched(dto.getNewPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "새 비밀번호는 기존 비밀번호와 다르게 설정해야 합니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        user.updatePassword(encodedPassword);
    }

    // 유저 삭제(회원탈퇴. 이메일&비밀번호 확인 필요)
    @Transactional
    public void delete(Long userId, UserDeleteRequestDto dto) {
        User user = getUser(userId);

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

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 존재하지 않습니다."));
    }

    private boolean isPasswordNotMatched(String inputPassword, String encodedPassword) {
        return !passwordEncoder.matches(inputPassword, encodedPassword);
    }

    private PageRequest createPageable(int page, int size) {
        int enablePage = (page > 0) ? page - 1 : 0;
        return PageRequest.of(enablePage, size, Sort.by("updatedAt").descending());
    }
}