package com.example.simplesns.domain.comment.entity;

import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

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

    private String imageUrl; // 댓글에 이미지가 있다면 저장할 URL

    @ManyToOne(fetch = FetchType.LAZY) // 대댓글 기능을 위해 부모 댓글 참조
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL) // 대댓글 리스트
    private List<Comment> replies = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 날짜

    // 생성자 (일반 댓글)
    public Comment(Post post, User user, String content, String imageUrl) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    // 생성자 (대댓글)
    public Comment(Post post, User user, String content, String imageUrl, Comment parent) {
        this.post = post;
        this.user = user;
        this.content = content;
        this.imageUrl = imageUrl;
        this.parent = parent;
    }
}
