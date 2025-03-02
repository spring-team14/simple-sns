package com.example.simplesns.domain.post.repository;

import com.example.simplesns.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    List<PostLike> findByUserIdAndPostIdIn(Long userId, List<Long> postIdList);

    void deleteByPostId(Long postId);
}
