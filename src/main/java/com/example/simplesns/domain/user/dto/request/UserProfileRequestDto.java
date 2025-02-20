package com.example.simplesns.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserProfileRequestDto {

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @NotBlank(message = "유저명은 필수 입력값입니다.")
    @Size(max = 5, message = "유저명은 5글자 이내여야 합니다.")
    private String name;

    @NotNull(message = "생년월일은 필수 입력값입니다.")
    private LocalDate birthdate;

    private String image;
    private String mbti;
}
