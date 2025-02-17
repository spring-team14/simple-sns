package com.example.simplesns.domain.post.dto;

public class PostRequestDto {
    private String title;
    private String content;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
