package com.example.simplesns.domain.friend.entity;

import com.example.simplesns.common.entity.BaseEntity;
import com.example.simplesns.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "friend")
@NoArgsConstructor
public class Friend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id")
    private User friend;

    public Friend(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }
}
