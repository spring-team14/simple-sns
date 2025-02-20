package com.example.simplesns.domain.comment.service;

import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.comment.dto.request.CommentRequestDto;
import com.example.simplesns.domain.comment.dto.response.CommentResponseDto;
import com.example.simplesns.domain.comment.entity.Comment;
import com.example.simplesns.domain.comment.entity.CommentLike;
import com.example.simplesns.domain.comment.repository.CommentLikeRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository; // ✅ 추가
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto dto, Long userId) { // ✅ userId로 변경
        Post post = findPost(postId);
        User user = findUser(userId);
        Comment comment = commentRepository.save(new Comment(post, user, dto.getContent()));
        post.increaseCommentCount();
        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public PaginationResponse<CommentResponseDto> findByPost(int page, int size, Long postId, Long userId) {
        findPost(postId);
        PageRequest pageable = createPageable(page, size);
        Page<Comment> commentsPages = commentRepository.findByPostIdAndDeletedAtIsNull(pageable, postId);

        List<Long> commentIdList = commentsPages.getContent()
                .stream()
                .map(Comment::getId)
                .toList();

        List<CommentLike> userLikedList = commentLikeRepository.findByUserIdAndCommentIdIn(userId, commentIdList);

        Set<Long> likedCommentIdList = userLikedList
                .stream()
                .map(commentLike -> commentLike.getComment().getId())
                .collect(Collectors.toSet());

        List<CommentResponseDto> commentResponseDtoList = commentsPages.stream()
                .map(comment -> {
                    CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
                    boolean likedByUser = likedCommentIdList.contains(comment.getId());
                    commentResponseDto.setLikedByUser(likedByUser);
                    return commentResponseDto;
                })
                .toList();

        return new PaginationResponse<>(
                new PageImpl<>(commentResponseDtoList, pageable, commentsPages.getTotalElements())
        );
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findOne(Long id, Long userId) {
        CommentResponseDto commentResponseDto = new CommentResponseDto(findComment(id));
        boolean userLiked = commentLikeRepository.existsByCommentIdAndUserId(id, userId);
        commentResponseDto.setLikedByUser(userLiked);
        return commentResponseDto;
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
        comment.getPost().decreaseCommentCount();
        commentRepository.deleteById(commentId);
        commentLikeRepository.deleteByCommentId(commentId);
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

