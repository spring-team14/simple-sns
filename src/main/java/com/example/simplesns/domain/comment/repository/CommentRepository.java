package com.example.simplesns.domain.comment.repository;

import com.example.simplesns.domain.comment.entity.Comment;
import com.example.simplesns.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 댓글을 가져오는 메서드
    List<Comment> findByPost(Post post);
}
