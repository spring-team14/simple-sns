package com.example.simplesns.domain.comment.dto;

import com.example.simplesns.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {
    private Long id;
    private String content;
    private String imageUrl;
    private String userName;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> replies;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.imageUrl = comment.getImageUrl();
        this.userName = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.replies = comment.getReplies().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}

