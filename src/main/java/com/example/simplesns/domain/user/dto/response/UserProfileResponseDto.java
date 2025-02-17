package com.example.simplesns.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserProfileResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final LocalDate birthdate;
    private final String image;
    private final String mbti;

    public UserProfileResponseDto(Long id, String email, String name, LocalDate birthdate, String image, String mbti) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthdate = birthdate;
        this.image = image;
        this.mbti = mbti;
    }
}
