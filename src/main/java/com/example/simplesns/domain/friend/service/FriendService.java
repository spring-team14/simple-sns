package com.example.simplesns.domain.friend.service;

import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.friend.dto.request.*;
import com.example.simplesns.domain.friend.dto.response.FriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.ReqFriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.createFriendReqResponseDto;
import com.example.simplesns.domain.friend.entity.Friend;
import com.example.simplesns.domain.friend.entity.FriendRequest;
import com.example.simplesns.domain.friend.entity.FriendStatus;
import com.example.simplesns.domain.friend.repository.FriendRepository;
import com.example.simplesns.domain.friend.repository.FriendRequestRepository;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Transactional
    public createFriendReqResponseDto createFriendReq(createFriendReqRequestDto dto) {
        User user = findUser(dto.getUserId()); // TODO 인증 로직 수정
        User friend = findUser(dto.getFriendId());
        // 기존 요청 이력 확인
        Optional<FriendRequest> findFriendRequest = friendRequestRepository.findByFromIdAndToId(user.getId(), friend.getId());
        if (findFriendRequest.isPresent()) {
            if (!findFriendRequest.get().getStatus().equals(FriendStatus.REJECT)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 데이터가 이미 존재합니다.");
            }
            // 기존 요청 이력이 '거절' 상태라면 기존 요청이력을 삭제
            friendRequestRepository.delete(findFriendRequest.get());
        }
        FriendRequest friendRequest = friendRequestRepository.save(new FriendRequest(user, friend, FriendStatus.WAIT));
        return createFriendReqResponseDto.of(friendRequest);
    }


    @Transactional(readOnly = true)
    public PaginationResponse<FriendResponseDto> findAll(int page, int size, FriendRequestDto dto) {
        PageRequest pageable = createPageable(page, size);
        Page<Friend> friendsPage = friendRepository.findAllByUserId(pageable, dto.getUserId()); // TODO 인증 로직 수정
        return new PaginationResponse<>(friendsPage.map(FriendResponseDto::new));
    }

    @Transactional(readOnly = true)
    public PaginationResponse<ReqFriendResponseDto> findAllRequest(int page, int size, ReqFriendRequestDto dto) {
        PageRequest pageable = createPageable(page, size);
        // TODO 인증 로직 수정
        Page<FriendRequest> friendRequestsPage = friendRequestRepository.findAllByToId(pageable, dto.getToId(), FriendStatus.WAIT);
        return new PaginationResponse<>(friendRequestsPage.map(ReqFriendResponseDto::new));
    }

    @Transactional
    public void signOffFriend(Long id, SignOffFriendRequestDto dto) {
        FriendRequest findFriendRequest = friendRequestRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "친구 요청 정보를 찾을 수 없습니다."));

        // 사용자가 거절한 경우
        if (FriendStatus.REJECT.equals(FriendStatus.of(dto.getStatus()))) {
            findFriendRequest.updateStatus(FriendStatus.REJECT);
            return;
        }
        // 사용자가 승인한 경우
        // TODO 인증 로직 수정
        User user = findUser(dto.getToId());
        User friend = findUser(findFriendRequest.getFrom().getId());
        // 친구 테이블의 정방향, 역방향 데이터 추가
        friendRepository.save(new Friend(user, friend));
        friendRepository.save(new Friend(friend, user));
        // 친구 요청 승인 변경
        findFriendRequest.updateStatus(FriendStatus.ACCEPT);
    }

    @Transactional
    public void deleteFriend(Long id) {
        // 요청 데이터 조회 및 삭제
        Friend friend = friendRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "친구 정보를 찾을 수 없습니다."));
        friendRepository.delete(friend);

        // 쌍이 되는 데이터 조회 및 삭제
        Friend pair = friendRepository.findByUserId(friend.getFriend().getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "친구 정보를 찾을 수 없습니다."));
        friendRepository.delete(pair);

        // 기존 요청 이력 확인
        FriendRequest findFriendRequest = friendRequestRepository.findByFromIdAndToId(friend.getUser().getId()
                , friend.getFriend().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        friendRequestRepository.delete(findFriendRequest);
    }

    private User findUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
        return user;
    }

    private PageRequest createPageable(int page, int size) {
        int enablePage = (page > 0) ? page - 1 : 0;
        return PageRequest.of(enablePage, size, Sort.by("updatedAt").descending());
    }
}
