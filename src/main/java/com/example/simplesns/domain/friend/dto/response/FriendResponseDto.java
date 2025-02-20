package com.example.simplesns.domain.friend.dto.response;

import com.example.simplesns.domain.friend.entity.Friend;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendResponseDto {
    private final Long id;
    private final Long friendId;
    private final String name;
    private final String image;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    public FriendResponseDto(Friend friend) {
        this.id = friend.getId();
        this.friendId = friend.getFriend().getId();
        this.name = friend.getFriend().getName();
        this.image = friend.getFriend().getImage();
        this.createdAt = friend.getCreatedAt();
        this.updatedAt = friend.getUpdatedAt();
    }
}
