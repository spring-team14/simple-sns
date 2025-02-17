package com.example.simplesns.domain.friend.dto.response;

import com.example.simplesns.domain.friend.entity.FriendRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReqFriendResponseDto {
    private final Long id;
    private final Long fromId;
    private final String name;
    private final String image;
    private final String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    public ReqFriendResponseDto(FriendRequest friendRequest) {
        this.id = friendRequest.getId();
        this.fromId = friendRequest.getFrom().getId();
        this.name = friendRequest.getFrom().getName();
        this.image = friendRequest.getFrom().getImage();
        this.status = friendRequest.getStatus().name();
        this.createdAt = friendRequest.getCreatedAt();
        this.updatedAt = friendRequest.getUpdatedAt();
    }
}
