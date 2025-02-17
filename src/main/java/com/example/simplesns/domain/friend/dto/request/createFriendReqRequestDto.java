package com.example.simplesns.domain.friend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class createFriendReqRequestDto {

    // TODO 인증 구현 후 삭제
    private Long userId;
    @NotNull(message = "친구 ID는 필수값입니다.")
    private Long friendId;
}
