package com.example.simplesns.domain.post.controller;

import com.example.simplesns.common.consts.Const;
import com.example.simplesns.domain.post.dto.PostRequestDto;
import com.example.simplesns.domain.post.dto.PostResponseDto;
import com.example.simplesns.domain.post.service.PostService;
import com.example.simplesns.common.dto.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 조회 (페이징 처리) - findAll 메서드로 페이징 처리
    @GetMapping("/posts")
    public ResponseEntity<PaginationResponse<PostResponseDto>> findAll(@RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @SessionAttribute(name = Const.LOGIN_USER) Long userId) {

        Pageable pageable = PageRequest.of(page - 1, size);

        // Service에서 페이징 처리된 데이터를 받아옴
        PaginationResponse<PostResponseDto> posts = postService.findAll(pageable, userId);

        return ResponseEntity.ok(posts);
    }

    // 게시글 생성
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> save(@RequestBody PostRequestDto dto,
                                                @SessionAttribute(name = Const.LOGIN_USER) Long userId) {
        PostResponseDto post = postService.save(dto, userId);
        return ResponseEntity.ok(post);
    }

    // ID로 게시글 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> findOne(@PathVariable Long id,
                                                   @SessionAttribute(name = Const.LOGIN_USER) Long userId) {
        PostResponseDto post = postService.findById(id, userId);
        return ResponseEntity.ok(post);
    }

    // 게시글 수정
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> update(
            @PathVariable Long id,
            @RequestBody PostRequestDto dto,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId) {
        PostResponseDto updatedPost = postService.update(id, userId, dto);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @SessionAttribute(name = Const.LOGIN_USER) Long userId) {
        postService.deleteById(id, userId);
        return ResponseEntity.noContent().build();
    }
}