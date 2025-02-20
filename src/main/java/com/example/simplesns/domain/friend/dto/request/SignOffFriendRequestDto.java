package com.example.simplesns.domain.friend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignOffFriendRequestDto {
    @NotNull(message = "승인 여부는 필수값입니다.")
    private Integer status;
}
