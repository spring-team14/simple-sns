package com.example.simplesns.domain.comment.dto;

import java.time.LocalDateTime;  // 추가된 import
import java.util.List;  // 추가된 import
import java.util.stream.Collectors;  // 추가된 import
import com.example.simplesns.domain.comment.entity.Comment;  // 추가된 import
import lombok.Getter;

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
        this.userName = (comment.getUser() != null) ? comment.getUser().getName() : "Anonymous"; // 수정된 부분
        this.createdAt = comment.getCreatedAt();
        this.replies = comment.getReplies() != null
                ? comment.getReplies().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList())
                : List.of(); // replies가 null이면 빈 리스트 반환
    }
}

