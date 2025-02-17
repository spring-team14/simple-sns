package com.example.simplesns.domain.post.service;

import com.example.simplesns.domain.post.dto.PostRequestDto;
import com.example.simplesns.domain.post.dto.PostResponseDto;
import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.post.repository.PostRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Post post = new Post(dto.getTitle(), dto.getContent(), user);
        Post savedPost = postRepository.save(post);

        // 생성된 Post의 ID, title, content, createdAt, updatedAt을 포함한 ResponseDto 반환
        return new PostResponseDto(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt()
        );
    }

    // 모든 게시글 조회
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();

        List<PostResponseDto> dtos = new ArrayList<>();
        for (Post post : posts) {
            // Post의 각 정보를 포함한 ResponseDto 생성
            PostResponseDto dto = new PostResponseDto(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            );
            dtos.add(dto);
        }

        return dtos;
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
                post.getUpdatedAt()
        );
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
                post.getUpdatedAt()
        );
    }

    // 게시글 삭제
    @org.springframework.transaction.annotation.Transactional
    public void deleteById(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("그 id가진 게시글이 없어서 삭제 못함");
        }

        postRepository.deleteById(id);
    }
}
