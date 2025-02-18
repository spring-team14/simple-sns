package com.example.simplesns.domain.post.controller;

import com.example.simplesns.common.consts.Const;
import com.example.simplesns.domain.post.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikesService postLikesService;

    @PostMapping("/api/posts/{postId}/likes")
    public ResponseEntity<String> toggleLike(
            @PathVariable Long postId,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        String message = postLikesService.toggleLike(postId, userId);
        return ResponseEntity.ok(message);
    }

}
