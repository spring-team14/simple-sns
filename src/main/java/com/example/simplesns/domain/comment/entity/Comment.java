package com.example.simplesns.domain.comment.entity;

import com.example.simplesns.common.entity.BaseEntity;
import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "comments")
@SQLDelete(sql = "UPDATE comments SET deleted_at = now() WHERE id = ?")
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 댓글은 하나의 게시글(Post)에 속함
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY) // 댓글을 작성한 사용자
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String content;  // 댓글 내용

    private LocalDateTime deletedAt;

    // 생성자 (일반 댓글)
    public Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
