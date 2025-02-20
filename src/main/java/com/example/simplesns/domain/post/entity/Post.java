package com.example.simplesns.domain.post.entity;


import com.example.simplesns.common.entity.BaseEntity;
import com.example.simplesns.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "posts")
@SQLDelete(sql = "UPDATE posts SET deleted_at = now() WHERE id = ?")

public class Post extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime deletedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private int likeCount = 0;
    private int commentCount = 0;

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseLikeCount() {
        this.likeCount ++;
    }

    public void decreaseLikeCount() {
        this.likeCount --;
    }

    public void increaseCommentCount() { this.commentCount ++; }

    public void decreaseCommentCount() { this.commentCount --; }
}