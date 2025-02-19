package com.example.simplesns.domain.post.controller;

import com.example.simplesns.common.consts.Const;
import com.example.simplesns.domain.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<String> toggleLike(
            @PathVariable Long postId,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        String message = postLikeService.toggleLike(postId, userId);
        return ResponseEntity.ok(message);
    }

}
