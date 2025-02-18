package com.example.simplesns.domain.post.dto;

public class PostRequestDto {
    private String title;
    private String content;
    private Long userId;



    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    public Long getUserId() {
        return userId;
    }
    public PostRequestDto(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
