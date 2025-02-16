package com.example.simplesns.domain.post.repository;

import com.example.simplesns.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
