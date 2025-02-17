package com.example.simplesns.domain.friend.dto.response;

import com.example.simplesns.domain.friend.entity.FriendRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class createFriendReqResponseDto {
    private final Long id;
    private final Long fromId;
    private final String fromName;
    private final Long toId;
    private final String toName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    public createFriendReqResponseDto(Long id, Long fromId, String fromName, Long toId, String toName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fromId = fromId;
        this.fromName = fromName;
        this.toId = toId;
        this.toName = toName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static createFriendReqResponseDto of(FriendRequest friendRequest) {
        return new createFriendReqResponseDto(
                friendRequest.getId(),
                friendRequest.getFrom().getId(),
                friendRequest.getFrom().getName(),
                friendRequest.getTo().getId(),
                friendRequest.getTo().getName(),
                friendRequest.getCreatedAt(),
                friendRequest.getUpdatedAt()
        );
    }
}
