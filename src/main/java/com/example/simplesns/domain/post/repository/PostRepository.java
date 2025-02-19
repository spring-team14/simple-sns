package com.example.simplesns.domain.post.repository;

import com.example.simplesns.domain.post.entity.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // UserId로 게시글을 찾는 메소드 추가 (예시로 작성)
    Page<Post> findAllByUserId(PageRequest pageable, Long userId);

    // 특정 게시글을 UserId로 찾는 예시 메소드 추가
    Optional<Post> findByUserId(Long userId);

}
