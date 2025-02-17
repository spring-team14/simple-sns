package com.example.simplesns.domain.friend.repository;

import com.example.simplesns.domain.friend.entity.FriendRequest;
import com.example.simplesns.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("SELECT f1 " +
            "FROM FriendRequest f1 " +
            "WHERE f1.from = :user " +
            "AND f1.to = :friend " +
            "UNION " +
            "SELECT f2 " +
            "FROM FriendRequest f2 " +
            "WHERE f2.to = :user " +
            "AND f2.from = :friend ")
    Optional<FriendRequest> findByFromIdAndToId(User user, User friend);
}
