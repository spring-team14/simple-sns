package com.example.simplesns.domain.friend.repository;

import com.example.simplesns.domain.friend.entity.FriendRequest;
import com.example.simplesns.domain.friend.util.FriendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query("""
            SELECT fr1
            FROM FriendRequest fr1
            WHERE fr1.from.id = :userId
            AND fr1.to.id = :friendId
            UNION
            SELECT fr2
            FROM FriendRequest fr2
            WHERE fr2.to.id = :userId
            AND fr2.from.id = :friendId""")
    Optional<FriendRequest> findByFromIdAndToId(Long userId, Long friendId);

    @Query("""
            SELECT fr
            From FriendRequest fr
            WHERE fr.to.id = :toId
            AND (:status IS NULL OR fr.status IN :status)
            AND (:email IS NULL OR fr.from.email LIKE %:email%)
            AND (:name IS NULL OR fr.from.name LIKE %:name%)""")
    Page<FriendRequest> findAllByToId(PageRequest pageable,
                                      Long toId,
                                      @Param("status") List<FriendStatus> status,
                                      @Param("email") String email,
                                      @Param("name") String name);
}
