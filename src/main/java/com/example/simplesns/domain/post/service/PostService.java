package com.example.simplesns.domain.post.service;

import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.post.dto.request.PostRequestDto;
import com.example.simplesns.domain.post.dto.response.PostResponseDto;
import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.post.entity.PostLike;
import com.example.simplesns.domain.post.repository.PostLikeRepository;
import com.example.simplesns.domain.post.repository.PostRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import com.example.simplesns.exception.custom.auth.UnauthorizedException;
import com.example.simplesns.exception.custom.post.PostDeletedException;
import com.example.simplesns.exception.custom.post.PostNotFoundException;
import com.example.simplesns.exception.custom.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    // 게시글 생성
    @Transactional
    public PostResponseDto save(PostRequestDto dto, Long userId) {
        // user 존재 여부 확인
        User user = findUser(userId);

        // Post 객체 생성
        Post post = new Post(dto.getTitle(), dto.getContent(), user);
        Post savedPost = postRepository.save(post);

        // 생성된 Post의 ID, title, content, createdAt, updatedAt을 포함한 ResponseDto 반환
        return new PostResponseDto(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt(),
                savedPost.getUser().getId(),
                savedPost.getLikeCount()
        );
    }

    // 게시글 조회 (페이징 처리) - Pageable 사용
    @Transactional(readOnly = true)
    public PaginationResponse<PostResponseDto> findAll(int page, int size, Long userId) {
        // Post 게시글을 페이징하여 조회
        PageRequest pageable = createPageable(page, size);
        Page<Post> postsPage = postRepository.findByDeletedAtIsNull(pageable);

        List<Long> postIdList = postsPage.getContent()
                .stream()
                .map(Post::getId)
                .toList();

        List<PostLike> userLikedList = postLikeRepository.findByUserIdAndPostIdIn(userId, postIdList);

        Set<Long> likedPostIdList = userLikedList
                .stream()
                .map(postLike -> postLike.getPost().getId())
                .collect(Collectors.toSet());

        List<PostResponseDto> postResponseDtoList = postsPage.stream()
                .map(post -> {
                    PostResponseDto postResponseDto = new PostResponseDto(post);
                    boolean likedByUser = likedPostIdList.contains(post.getId());
                    postResponseDto.setLikedByUser(likedByUser);
                    return postResponseDto;
                })
                .toList();

        // 결과를 PaginationResponse에 래핑하여 반환
        return new PaginationResponse<>(
                new PageImpl<>(postResponseDtoList, pageable, postsPage.getTotalElements())
        );
    }

    // ID로 게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id, Long userId) {
        PostResponseDto postResponseDto = new PostResponseDto(findPost(id));
        boolean userLiked = postLikeRepository.existsByPostIdAndUserId(id, userId);
        postResponseDto.setLikedByUser(userLiked);
        // 조회된 Post의 정보를 포함한 ResponseDto 반환
        return postResponseDto;
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto update(Long id, Long userId, PostRequestDto dto) {
        Post post = findPost(id);
        if (!post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException();
        }

        // Post의 내용 수정
        post.update(dto.getTitle(), dto.getContent()); // 영속성 컨텍스트 관리

        // 수정된 Post의 정보를 포함한 ResponseDto 반환
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getUser().getId(),
                post.getLikeCount());
    }

    // 게시글 삭제
    @Transactional
    public void deleteById(Long id, Long userId) {
        Post post = findPost(id);
        if (!post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException();
        }

        postRepository.deleteById(id);
        postLikeRepository.deleteByPostId(id);
    }

    // PageRequest 생성 (페이징 처리에 필요한 메소드)
    private PageRequest createPageable(int page, int size) {
        int enablePage = (page > 0) ? page - 1 : 0;  // 페이지 번호는 0부터 시작하므로 1을 빼줍니다.
        return PageRequest.of(enablePage, size, Sort.by("updatedAt").descending());  // 최신 게시글부터 정렬
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
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
}