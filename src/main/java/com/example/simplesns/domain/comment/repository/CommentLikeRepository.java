package com.example.simplesns.domain.comment.repository;

import com.example.simplesns.domain.comment.entity.Comment;
import com.example.simplesns.domain.comment.entity.CommentLike;
import com.example.simplesns.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserIdAndCommentId(Long userId, Long commentId);

    List<CommentLike> findByUserIdAndCommentIdIn(Long userId, List<Long> commentIdList);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);

    Long comment(Comment comment);

    Long user(User user);
}
