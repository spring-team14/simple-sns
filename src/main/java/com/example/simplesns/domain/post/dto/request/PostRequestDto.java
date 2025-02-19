package com.example.simplesns.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDto {
    @NotBlank(message = "게시글 제목은 필수 입력값입니다.")
    private String title;
    @NotBlank(message = "게시글 내용은 필수 입력값입니다.")
    private String content;

    public PostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
