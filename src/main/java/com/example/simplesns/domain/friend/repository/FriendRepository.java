package com.example.simplesns.domain.friend.repository;

import com.example.simplesns.domain.friend.entity.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("""
            SELECT f
            FROM Friend f
            WHERE f.user.id = :userId
            AND (:email IS NULL OR f.friend.email LIKE %:email%)
            AND (:name IS NULL OR f.friend.name LIKE %:name%)
            """)
    Page<Friend> findAllByUserId(PageRequest pageable,
                                 Long userId,
                                 @Param("email") String email,
                                 @Param("name") String name);

    Optional<Friend> findByUserIdAndFriendId(Long pairId, Long userId);

    @Query("""
        SELECT f.friend.id
        FROM Friend f
        WHERE f.user.id = :userId
        AND (:friendId IS NULL OR f.friend.id =:friendId)
        """)
    List<Long> findFriendIdsByUserId(Long userId, Long friendId);
}
