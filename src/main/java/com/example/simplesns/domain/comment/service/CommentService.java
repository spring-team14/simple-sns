package com.example.simplesns.domain.comment.service;

import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.comment.dto.request.CommentRequestDto;
import com.example.simplesns.domain.comment.dto.response.CommentResponseDto;
import com.example.simplesns.domain.comment.entity.Comment;
import com.example.simplesns.domain.comment.repository.CommentRepository;
import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.post.repository.PostRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import com.example.simplesns.exception.custom.auth.UnauthorizedException;
import com.example.simplesns.exception.custom.comment.CommentDeletedException;
import com.example.simplesns.exception.custom.comment.CommentNotFoundException;
import com.example.simplesns.exception.custom.post.PostDeletedException;
import com.example.simplesns.exception.custom.post.PostNotFoundException;
import com.example.simplesns.exception.custom.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository; // ✅ 추가

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto dto, Long userId) { // ✅ userId로 변경
        Post post = findPost(postId);
        User user = findUser(userId);
        Comment comment = commentRepository.save(new Comment(post, user, dto.getContent()));
        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public PaginationResponse<CommentResponseDto> findByPost(int page, int size, Long postId) {
        findPost(postId);
        PageRequest pageable = createPageable(page, size);
        Page<Comment> commentsPages = commentRepository.findByPostId(pageable, postId);
        return new PaginationResponse<>(commentsPages.map(CommentResponseDto::new));
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findOne(Long id) {
        return new CommentResponseDto(findComment(id));
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto dto, Long userId) {
        Comment comment = findComment(id);
        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedException();
        }
        comment.update(dto.getContent());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = findComment(commentId);
        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedException();
        }
        commentRepository.deleteById(commentId);
    }

    private Comment findComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        if (comment.getDeletedAt() != null) {
            throw new CommentDeletedException(commentId);
        }
        return comment;
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId) // ✅ userId로 User 가져오기
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Post findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        if (post.getDeletedAt() != null) {
            throw new PostDeletedException(postId);
        }
        return post;
    }

    private PageRequest createPageable(int page, int size) {
        int enablePage = (page > 0) ? page - 1 : 0;
        return PageRequest.of(enablePage, size, Sort.by("updatedAt").descending());
    }
}

