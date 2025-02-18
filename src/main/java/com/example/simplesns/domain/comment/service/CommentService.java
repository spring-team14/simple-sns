package com.example.simplesns.domain.comment.service;

import com.example.simplesns.domain.comment.dto.CommentRequestDto;
import com.example.simplesns.domain.comment.dto.CommentResponseDto;
import com.example.simplesns.domain.comment.entity.Comment;
import com.example.simplesns.domain.comment.repository.CommentRepository;
import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.post.repository.PostRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository; // ✅ 추가

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, Long userId) { // ✅ userId로 변경
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        User user = userRepository.findById(userId) // ✅ userId로 User 가져오기
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment parentComment = null;
        if (requestDto.getParentId() != null) {
            parentComment = commentRepository.findById(requestDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = new Comment(post, user, requestDto.getContent(), requestDto.getImageUrl(), parentComment);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        return commentRepository.findByPost(post).stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}

