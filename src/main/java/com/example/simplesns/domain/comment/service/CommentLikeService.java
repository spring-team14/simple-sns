package com.example.simplesns.domain.comment.service;

import com.example.simplesns.domain.comment.entity.Comment;
import com.example.simplesns.domain.comment.entity.CommentLike;
import com.example.simplesns.domain.comment.repository.CommentLikeRepository;
import com.example.simplesns.domain.comment.repository.CommentRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import com.example.simplesns.exception.custom.comment.CommentDeletedException;
import com.example.simplesns.exception.custom.comment.CommentNotFoundException;
import com.example.simplesns.exception.custom.like.SelfLikeNotAllowedException;
import com.example.simplesns.exception.custom.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public String toggleLike(Long commentId, Long userId) {
        Comment comment = findComment(commentId);
        if (comment.getUser().getId().equals(userId)) {
            throw new SelfLikeNotAllowedException("본인 댓글에는 좋아요를 누를 수 없습니다.");
        }

        Optional<CommentLike> commentLike = commentLikeRepository.findByUserIdAndCommentId(userId, commentId);
        if (commentLike.isPresent()) {
            commentLikeRepository.delete(commentLike.get());

            comment.decreaseLikeCount();

            return "좋아요 취소";
        } else {
            User user = findUser(userId);
            commentLikeRepository.save(new CommentLike(user, comment));

            comment.increaseLikeCount();

            return "좋아요 등록";
        }
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Comment findComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        if (comment.getDeletedAt() != null) {
            throw new CommentDeletedException(commentId);
        }
        return comment;
    }
}
