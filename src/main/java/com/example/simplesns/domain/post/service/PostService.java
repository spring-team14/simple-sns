package com.example.simplesns.domain.post.service;

import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.post.dto.PostRequestDto;
import com.example.simplesns.domain.post.dto.PostResponseDto;
import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.post.repository.PostRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 생성
    @Transactional
    public PostResponseDto save(PostRequestDto dto) {
        // user 존재 여부 확인
        User user = userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 user입니다")
        );

        // Post 객체 생성
        Post post = new Post(dto.getTitle(), dto.getContent(), user, null);  // deletedAt을 null로 설정
        Post savedPost = postRepository.save(post);

        // 생성된 Post의 ID, title, content, createdAt, updatedAt을 포함한 ResponseDto 반환
        return new PostResponseDto(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt(),
                savedPost.getUser().getId());
    }

    // 게시글 조회 (페이징 처리) - Pageable 사용
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PaginationResponse<PostResponseDto> findAll(Pageable pageable) {
        // Post 게시글을 페이징하여 조회
        Page<Post> postsPage = postRepository.findAll(pageable);

        // 결과를 PaginationResponse에 래핑하여 반환
        return new PaginationResponse<>(postsPage.map(post -> new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getUser().getId())));
    }

    // ID로 게시글 조회
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("그 id 게시글 없음")
        );

        // 조회된 Post의 정보를 포함한 ResponseDto 반환
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getUser().getId());
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto update(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("그 id 게시글 없음")
        );

        // Post의 내용 수정
        post.update(dto.getTitle(), dto.getContent()); // 영속성 컨텍스트 관리
        postRepository.save(post); // 변경된 내용 저장

        // 수정된 Post의 정보를 포함한 ResponseDto 반환
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getUser().getId());
    }

    // 게시글 삭제
    @Transactional
    public void deleteById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("그 id가진 게시글이 없어서 삭제 못함");
        }

        postRepository.deleteById(id);
    }

    // PageRequest 생성 (페이징 처리에 필요한 메소드)
    private PageRequest createPageable(int page, int size) {
        int enablePage = (page > 0) ? page - 1 : 0;  // 페이지 번호는 0부터 시작하므로 1을 빼줍니다.
        return PageRequest.of(enablePage, size, Sort.by("createdAt").descending());  // 최신 게시글부터 정렬
    }
}
