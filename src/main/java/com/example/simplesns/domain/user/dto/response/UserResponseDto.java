package com.example.simplesns.domain.user.dto.response;

import com.example.simplesns.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String email;
    private final String name;
    private final LocalDate birthdate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserResponseDto(Long id, String email, String name, LocalDate birthdate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.birthdate = birthdate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.birthdate = user.getBirthdate();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();

    }


}
