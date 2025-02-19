package com.example.simplesns.domain.post.dto.response;

import com.example.simplesns.domain.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long userId;
    private int likeCount;
    @Setter
    private boolean isLikedByUser;

    public PostResponseDto(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId, int likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.likeCount = likeCount;
    }

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.userId = post.getUser().getId();
        this.likeCount = post.getLikeCount();
    }
}
