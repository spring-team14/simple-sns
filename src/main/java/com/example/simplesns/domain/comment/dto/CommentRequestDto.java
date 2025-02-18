package com.example.simplesns.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private String imageUrl;
    private Long parentId; // 대댓글일 경우 부모 댓글 ID
}


