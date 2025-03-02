package com.example.simplesns.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserSaveRequestDto {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "유저명은 필수 입력값입니다.")
    @Size(max = 5, message = "유저명은 5글자 이내여야 합니다.")
    private String name;

    @NotNull(message = "생년월일은 필수 입력값입니다.")
    private LocalDate birthdate;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 5, max = 15, message = "비밀번호는 5자 이상 15자 이하로 입력해야 합니다.")
    private String password;
}
