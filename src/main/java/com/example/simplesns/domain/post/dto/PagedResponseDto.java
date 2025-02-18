package com.example.simplesns.domain.post.dto;

import com.example.simplesns.domain.post.entity.Post;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PagedResponseDto {

    private final List<PostResponseDto> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;
    private final boolean isLast;
    private final boolean isFirst;
    private final boolean isSorted;

    public PagedResponseDto(Page<Post> page) {
        // 'Page' 객체에서 페이징 정보와 데이터를 가져와서 설정
        this.content = page.getContent().stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getCreatedAt(),
                        post.getUpdatedAt(),
                        post.getUser().getId()))
                .collect(Collectors.toList());
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.isLast = page.isLast();
        this.isFirst = page.isFirst();
        this.isSorted = page.getSort().isSorted();
    }
}
