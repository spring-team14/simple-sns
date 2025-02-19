package com.example.simplesns.domain.post.service;

import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.post.entity.PostLike;
import com.example.simplesns.domain.post.repository.PostLikeRepository;
import com.example.simplesns.domain.post.repository.PostRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import com.example.simplesns.exception.custom.like.SelfLikeNotAllowedException;
import com.example.simplesns.exception.custom.post.PostDeletedException;
import com.example.simplesns.exception.custom.post.PostNotFoundException;
import com.example.simplesns.exception.custom.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public String toggleLike(Long postId, Long userId) {
        Post post = findPost(postId);
        if (post.getUser().getId().equals(userId)) {
            throw new SelfLikeNotAllowedException("본인 게시글에는 좋아요를 누를 수 없습니다.");
        }

        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(postId, userId);
        if (postLike.isPresent()) {
            postLikeRepository.delete(postLike.get());

            // TODO: post Entity 좋아요 수 감소
            // post.decreaseLikeCount();

            return "좋아요 취소";
        } else {
            User user = findUser(userId);
            postLikeRepository.save(new PostLike(user, post));

            // TODO: post Entity 좋아요 수 증가
            // post.increaseLikeCount();

            return "좋아요 등록";
        }
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Post findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        if (post.getDeletedAt() != null) {
            throw new PostDeletedException(postId);
        }
        return post;
    }
}
