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
    private final String content;
    private final Integer likeCount;
    private final Integer commentCount;
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
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
