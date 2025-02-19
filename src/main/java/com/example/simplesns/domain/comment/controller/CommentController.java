package com.example.simplesns.domain.comment.controller;

import com.example.simplesns.common.consts.Const;
import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.comment.dto.request.CommentRequestDto;
import com.example.simplesns.domain.comment.dto.response.CommentResponseDto;
import com.example.simplesns.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId,
                                                            @RequestBody CommentRequestDto dto,
                                                            @SessionAttribute(name = Const.LOGIN_USER) Long userId) { // ✅ userId 추가
        return ResponseEntity.ok(commentService.createComment(postId, dto, userId));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<PaginationResponse<CommentResponseDto>> findByPost(@PathVariable Long postId,
                                                                             @RequestParam(defaultValue = "1") int page,
                                                                             @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.findByPost(page, size, postId));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findOne(id));
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
                                              @RequestBody CommentRequestDto dto,
                                              @SessionAttribute(name = Const.LOGIN_USER) Long userId) {
        return ResponseEntity.ok(commentService.updateComment(id, dto, userId));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id,
                                              @SessionAttribute(name = Const.LOGIN_USER) Long userId) {
        commentService.deleteComment(id, userId);
        return ResponseEntity.ok().build();
    }
}

