package com.example.simplesns.domain.comment.controller;

import com.example.simplesns.common.consts.Const;
import com.example.simplesns.domain.comment.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<String> toggleLike(
            @PathVariable Long commentId,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        String message = commentLikeService.toggleLike(commentId, userId);
        return ResponseEntity.ok(message);
    }
}
