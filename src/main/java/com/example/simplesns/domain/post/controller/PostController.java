package com.example.simplesns.domain.post.controller;

import com.example.simplesns.domain.post.dto.PostRequestDto;
import com.example.simplesns.domain.post.dto.PostResponseDto;
import com.example.simplesns.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시물 작성 (POST)
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = postService.createPost(postRequestDto);
        return ResponseEntity.ok(postResponseDto);
    }

    // 게시물 조회 (GET)
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        PostResponseDto postResponseDto = postService.getPost(id);
        return ResponseEntity.ok(postResponseDto);
    }

    // 게시물 수정 (PUT)
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                      @RequestBody PostRequestDto postRequestDto,
                                                      @RequestParam String currentUser) {
        PostResponseDto postResponseDto = postService.updatePost(id, postRequestDto, currentUser);
        return ResponseEntity.ok(postResponseDto);
    }

    // 게시물 삭제 (DELETE)
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, @RequestParam String currentUser) {
        postService.deletePost(id, currentUser);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
