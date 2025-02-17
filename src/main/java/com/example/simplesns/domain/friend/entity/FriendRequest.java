package com.example.simplesns.domain.friend.entity;

import com.example.simplesns.common.entity.BaseEntity;
import com.example.simplesns.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "friend_request")
@NoArgsConstructor
public class FriendRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "from_id")
    private User from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id")
    private User to;

    @Convert(converter = FriendStatusConverter.class)
    private FriendStatus status;

    public FriendRequest(User from, User to, FriendStatus status) {
        this.from = from;
        this.to = to;
        this.status = status;
    }
}
