package com.example.simplesns.domain.post.service;

import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.post.entity.PostLike;
import com.example.simplesns.domain.post.repository.PostLikesRepository;
import com.example.simplesns.domain.post.repository.PostRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikesService {

    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public String toggleLike(Long postId, Long userId) {
        Optional<PostLike> postLike = postLikesRepository.findByPostIdAndUserId(postId, userId);

        if (postLike.isPresent()) {
            postLikesRepository.delete(postLike.get());
            decreaseLikeCount(postId);
            return "좋아요 취소";
        }
        else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 유저가 존재하지 않습니다."));
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("그 id 게시글 없음")
            );

            postLikesRepository.save(new PostLike(user, post));
            increaseLikeCount(postId);
            return "좋아요 등록";
        }
    }

    private void increaseLikeCount(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("그 id 게시글 없음")
        );
        //post.increaseLikeCount();
    }
    private void decreaseLikeCount(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("그 id 게시글 없음")
        );
        //post.decreaseLikeCount();
    }
}
