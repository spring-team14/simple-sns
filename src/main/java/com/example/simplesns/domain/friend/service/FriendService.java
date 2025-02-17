package com.example.simplesns.domain.friend.service;

import com.example.simplesns.domain.friend.dto.common.PaginationResponse;
import com.example.simplesns.domain.friend.dto.request.*;
import com.example.simplesns.domain.friend.dto.response.FriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.ReqFriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.createFriendReqResponseDto;
import com.example.simplesns.domain.friend.entity.FriendRequest;
import com.example.simplesns.domain.friend.entity.FriendStatus;
import com.example.simplesns.domain.friend.repository.FriendRepository;
import com.example.simplesns.domain.friend.repository.FriendRequestRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;

    public createFriendReqResponseDto createFriendReq(createFriendReqRequestDto dto) {
        User user = findUser(dto.getUserId());
        User friend = findUser(dto.getFriendId());
        // 기존 요청 이력 확인
        Optional<FriendRequest> findFriendRequest = friendRequestRepository.findByFromIdAndToId(user, friend);
        if (findFriendRequest.isPresent()) {
            if (!findFriendRequest.get().getStatus().equals(FriendStatus.of(2))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 데이터가 이미 존재합니다.");
            }
        }
        FriendRequest friendRequest = friendRequestRepository.save(new FriendRequest(user, friend, FriendStatus.WAIT));
        return new createFriendReqResponseDto(friendRequest.getId(),
                friendRequest.getFrom().getId(),
                friendRequest.getFrom().getName(),
                friendRequest.getTo().getId(),
                friendRequest.getTo().getName(),
                friendRequest.getCreatedAt(),
                friendRequest.getUpdatedAt());
    }

    private User findUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
        return user;
    }

    public PaginationResponse<FriendResponseDto> findAll(FriendRequestDto dto) {
        return null;
    }

    public PaginationResponse<ReqFriendResponseDto> findAllRequest(ReqFriendRequestDto dto) {
        return null;
    }

    public void signOffFriend(Long toId, SignOffFriendRequestDto dto) {
    }

    public void deleteFriend(Long friendId, DeleteFriendRequestDto dto) {

    }
}
