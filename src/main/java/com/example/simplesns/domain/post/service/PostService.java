package com.example.simplesns.domain.post.service;

import com.example.simplesns.domain.post.dto.PostRequestDto;
import com.example.simplesns.domain.post.dto.PostResponseDto;
import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시물 작성
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent(), postRequestDto.getAuthor());
        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost.getId(), savedPost.getTitle(), savedPost.getContent(), savedPost.getAuthor(), savedPost.getCreatedDate());
    }

    // 게시물 조회
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getAuthor(), post.getCreatedDate());
    }

    // 게시물 수정
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, String currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 작성자가 아닌 경우 예외 처리
        if (!post.getAuthor().equals(currentUser)) {
            throw new RuntimeException("작성자가 아닙니다.");
        }

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        Post updatedPost = postRepository.save(post);

        return new PostResponseDto(updatedPost.getId(), updatedPost.getTitle(), updatedPost.getContent(), updatedPost.getAuthor(), updatedPost.getCreatedDate());
    }

    // 게시물 삭제
    public void deletePost(Long id, String currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 작성자가 아닌 경우 예외 처리
        if (!post.getAuthor().equals(currentUser)) {
            throw new RuntimeException("작성자가 아닙니다");
        }

        postRepository.delete(post);
    }
}
