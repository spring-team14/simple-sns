package com.example.simplesns.domain.user.controller;

import com.example.simplesns.common.consts.Const;
import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.user.dto.request.UserDeleteRequestDto;
import com.example.simplesns.domain.user.dto.request.UserPasswordUpdateRequestDto;
import com.example.simplesns.domain.user.dto.request.UserProfileRequestDto;
import com.example.simplesns.domain.user.dto.request.UserSaveRequestDto;
import com.example.simplesns.domain.user.dto.response.UserProfileResponseDto;
import com.example.simplesns.domain.user.dto.response.UserResponseDto;
import com.example.simplesns.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성(회원가입)
    @PostMapping("/users/signup")
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody UserSaveRequestDto dto) {
        return ResponseEntity.ok(userService.sava(dto));
    }

    // 전체 유저 목록 조회
    @GetMapping("/users")
    public ResponseEntity<PaginationResponse<UserResponseDto>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.findAll(page, size));
    }

    // 특정 유저 프로필 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserProfileResponseDto> findOne(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findOne(userId));
    }

    // 특정 유저 프로필 수정(이메일 수정 불가, 비밀번호 확인 필요)
    @PatchMapping("/users")
    public ResponseEntity<UserProfileResponseDto> updateProfile(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody UserProfileRequestDto dto) {
        return ResponseEntity.ok(userService.updateProfile(userId, dto));
    }

    // 특정 유저 비밀번호 수정
    @PatchMapping("/users/password")
    public void updatePassword(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody UserPasswordUpdateRequestDto dto) {
       userService.updatePassword(userId, dto);
    }

    // 유저 삭제(회원탈퇴. 이메일&비밀번호 확인 필요)
    @DeleteMapping("/users")
    public void delete(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Valid @RequestBody UserDeleteRequestDto dto) {
        userService.delete(userId, dto);
    }
}
