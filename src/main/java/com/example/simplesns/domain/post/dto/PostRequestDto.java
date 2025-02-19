package com.example.simplesns.domain.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;

    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
