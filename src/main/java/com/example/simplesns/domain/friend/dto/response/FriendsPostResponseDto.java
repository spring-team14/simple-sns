package com.example.simplesns.domain.friend.dto.response;

import com.example.simplesns.domain.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendsPostResponseDto {
    private final Long id;
    private final Long userId;
    private final String name;
    private final String profileImage;
    private final String title;
    private final String image;
    private final String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;

    public FriendsPostResponseDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.name = post.getUser().getName();
        this.profileImage = post.getUser().getImage();
        this.title = post.getTitle();
//        this.image = post.getImage();
        this.image = null;
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    public FriendsPostResponseDto(Long id, Long userId, String name, String profileImage, String title, String image, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.title = title;
        this.image = image;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
