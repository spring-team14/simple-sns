package com.example.simplesns.domain.friend.repository;

import com.example.simplesns.domain.friend.entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Page<Friend> findAllByUserId(PageRequest pageable, Long userId);

    Optional<Friend> findByUserId(Long userId);
}
