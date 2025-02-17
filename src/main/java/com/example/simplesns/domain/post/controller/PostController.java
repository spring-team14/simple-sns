package com.example.simplesns.domain.post.controller;

import com.example.simplesns.domain.post.dto.PostRequestDto;
import com.example.simplesns.domain.post.dto.PostResponseDto;
import com.example.simplesns.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> save(@RequestBody PostRequestDto dto) {
        return ResponseEntity.ok(postService.save(dto));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }
    @PutMapping("posts/{id}")
    public ResponseEntity<PostResponseDto> update(
            @PathVariable Long id,
            @RequestBody PostRequestDto dto
    ) {
        return ResponseEntity.ok(postService.update(id, dto));
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable Long id) {
        postService.deleteById(id);

    }
}
