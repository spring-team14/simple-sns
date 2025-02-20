package com.example.simplesns.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserPasswordUpdateRequestDto {

    @NotBlank(message = "현재 비밀번호는 필수 입력값입니다.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호는 필수 입력값입니다.")
    @Size(min = 5, max = 15, message = "비밀번호는 5자 이상 15자 이하로 입력해야 합니다.")
    private String newPassword;

    public UserPasswordUpdateRequestDto(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
