package com.example.simplesns.domain.friend.repository;

import com.example.simplesns.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FriendPostRepository extends JpaRepository<Post, Long> {
    @Query("""
        SELECT p
        FROM Post p
        WHERE p.user.id IN :friendIds
        AND (:fromAt IS NULL OR p.createdAt >= :fromAt)
        AND (:toAt IS NULL OR p.createdAt < :toAt)
        """)
    Page<Post> findAllByUserId(PageRequest pageable,
                               @Param("friendIds") List<Long> friendIds,
                               @Param("fromAt") LocalDate fromAt,
                               @Param("toAt") LocalDate toAt);
}
