package com.example.simplesns.domain.friend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class createFriendReqRequestDto {
    @NotNull(message = "친구 ID는 필수값입니다.")
    private Long friendId;
}
