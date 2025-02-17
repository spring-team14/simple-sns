package com.example.simplesns.domain.friend.repository;

import com.example.simplesns.domain.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
