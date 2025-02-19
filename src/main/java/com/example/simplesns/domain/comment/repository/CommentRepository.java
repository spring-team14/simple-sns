package com.example.simplesns.domain.comment.repository;

import com.example.simplesns.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 댓글을 가져오는 메서드
    Page<Comment> findByPostIdAndDeletedAtIsNull(PageRequest pageable, Long postId);
}

