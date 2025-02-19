package com.example.simplesns.domain.comment.dto.response;

import java.time.LocalDateTime;  // 추가된 import

import com.example.simplesns.domain.comment.entity.Comment;  // 추가된 import
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentResponseDto {
    private Long id;
    private Long postId;
    private String content;
    private Long userId;
    private String name;
    private int likeCount;
    @Setter
    private boolean isLikedByUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.name = comment.getUser().getName();
        this.likeCount = comment.getLikeCount();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}

