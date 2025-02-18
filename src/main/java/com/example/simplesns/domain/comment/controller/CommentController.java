package com.example.simplesns.domain.comment.controller;

import com.example.simplesns.domain.comment.dto.CommentRequestDto;
import com.example.simplesns.domain.comment.dto.CommentResponseDto;
import com.example.simplesns.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponseDto createComment(@PathVariable Long postId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @RequestParam Long userId) { // ✅ userId 추가
        return commentService.createComment(postId, requestDto, userId);
    }

    @GetMapping
    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }
}

