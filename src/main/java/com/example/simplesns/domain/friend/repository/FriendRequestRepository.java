package com.example.simplesns.domain.friend.repository;

import com.example.simplesns.domain.friend.entity.FriendRequest;
import com.example.simplesns.domain.friend.entity.FriendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("""
            SELECT f1
            FROM FriendRequest f1
            WHERE f1.from.id = :userId
            AND f1.to.id = :friendId
            UNION
            SELECT f2
            FROM FriendRequest f2
            WHERE f2.to.id = :userId
            AND f2.from.id = :friendId""")
    Optional<FriendRequest> findByFromIdAndToId(Long userId, Long friendId);

    @Query("SELECT f From FriendRequest f WHERE f.to.id = :toId AND f.status = :wait")
    Page<FriendRequest> findAllByToId(PageRequest pageable, Long toId, FriendStatus wait);
}
