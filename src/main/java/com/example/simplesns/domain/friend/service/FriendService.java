package com.example.simplesns.domain.friend.service;

import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.friend.dto.request.*;
import com.example.simplesns.domain.friend.dto.response.FriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.FriendsPostResponseDto;
import com.example.simplesns.domain.friend.dto.response.ReqFriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.createFriendReqResponseDto;
import com.example.simplesns.domain.friend.entity.Friend;
import com.example.simplesns.domain.friend.entity.FriendRequest;
import com.example.simplesns.domain.friend.util.FriendStatus;
import com.example.simplesns.domain.friend.repository.FriendPostRepository;
import com.example.simplesns.domain.friend.repository.FriendRepository;
import com.example.simplesns.domain.friend.repository.FriendRequestRepository;
import com.example.simplesns.domain.post.entity.Post;
import com.example.simplesns.domain.user.entity.User;
import com.example.simplesns.domain.user.repository.UserRepository;
import com.example.simplesns.exception.custom.auth.UnauthorizedException;
import com.example.simplesns.exception.custom.friend.*;
import com.example.simplesns.exception.custom.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendPostRepository friendPostRepository;

    @Transactional
    public createFriendReqResponseDto createFriendReq(Long userId, createFriendReqRequestDto dto) {
        // 본인을 친구 추가한 경우
        if (userId.equals(dto.getFriendId())) {
            throw new FriendRequestToSelfException(userId);
        }
        User user = findUser(userId);
        User friend = findUser(dto.getFriendId());
        // 기존 요청 이력 확인
        Optional<FriendRequest> findFriendRequest = friendRequestRepository.findByFromIdAndToId(user.getId(), friend.getId());
        if (findFriendRequest.isPresent()) {
            if (!findFriendRequest.get().getStatus().equals(FriendStatus.REJECT)) {
                throw new FriendRequestAlreadyExistException();
            }
            // 기존 요청 이력이 '거절' 상태라면 기존 요청이력을 삭제
            friendRequestRepository.delete(findFriendRequest.get());
        }
        FriendRequest friendRequest = friendRequestRepository.save(new FriendRequest(user, friend, FriendStatus.WAIT));
        return createFriendReqResponseDto.of(friendRequest);
    }

    @Transactional(readOnly = true)
    public PaginationResponse<ReqFriendResponseDto> findAllRequest(int page, int size, Long userId, ReqFriendRequestDto dto) {
        PageRequest pageable = createPageable(page, size);
        List<FriendStatus> statusList = new ArrayList<>();

        if (dto.getStatus() == null) {
            statusList = List.of(FriendStatus.values());
        } else {
            statusList.add(FriendStatus.of(dto.getStatus()));
        }

        Page<FriendRequest> friendRequestsPage = friendRequestRepository.findAllByToId(pageable,
                userId,
                statusList,
                dto.getEmail(),
                dto.getName());
        return new PaginationResponse<>(friendRequestsPage.map(ReqFriendResponseDto::new));
    }

    @Transactional
    public void signOffFriend(Long id, Long userId, SignOffFriendRequestDto dto) {
        FriendRequest findFriendRequest = friendRequestRepository.findById(id).orElseThrow(FriendRequestNotFoundException::new);

        // 피요청자 == 로그인 유저 검증
        if (!findFriendRequest.getTo().getId().equals(userId)) {
            throw new UnauthorizedException();
        }

        // 대기 상태의 요청만 처리 가능
        if (!FriendStatus.WAIT.equals(findFriendRequest.getStatus())) {
            throw new FriendStatusNotWaitException();
        }

        // 사용자가 거절한 경우
        if (FriendStatus.REJECT.equals(FriendStatus.of(dto.getStatus()))) {
            findFriendRequest.updateStatus(FriendStatus.REJECT);
            return;
        }
        // 사용자가 승인한 경우
        User user = findUser(userId);
        User friend = findUser(findFriendRequest.getFrom().getId());

        // 친구 테이블의 정방향, 역방향 데이터 추가
        friendRepository.save(new Friend(user, friend));
        friendRepository.save(new Friend(friend, user));

        // 친구 요청 승인 변경
        findFriendRequest.updateStatus(FriendStatus.ACCEPT);
    }

    @Transactional(readOnly = true)
    public PaginationResponse<FriendResponseDto> findAll(int page, int size, Long userId, FindFriendRequestDto dto) {
        PageRequest pageable = createPageable(page, size);
        Page<Friend> friendsPage = friendRepository.findAllByUserId(pageable, userId, dto.getEmail(), dto.getName());
        return new PaginationResponse<>(friendsPage.map(FriendResponseDto::new));
    }

    @Transactional
    public void deleteFriend(Long id, Long userId) {
        // 요청 데이터 조회 및 삭제
        Friend friend = friendRepository.findById(id).orElseThrow(FriendNotFoundException::new);
        if (!friend.getUser().getId().equals(userId)) {
            throw new UnauthorizedException();
        }
        friendRepository.delete(friend);

        // 쌍이 되는 데이터 조회 및 삭제
        Long pairId = friend.getFriend().getId();
        Friend pair = friendRepository.findByUserIdAndFriendId(pairId, userId).orElseThrow(FriendNotFoundException::new);
        friendRepository.delete(pair);

        // 기존 요청 이력 확인
        FriendRequest findFriendRequest = friendRequestRepository.findByFromIdAndToId(friend.getUser().getId()
                , friend.getFriend().getId()).orElseThrow(FriendRequestNotFoundException::new);
        friendRequestRepository.delete(findFriendRequest);
    }

    @Transactional(readOnly = true)
    public PaginationResponse<FriendsPostResponseDto> findFriendsPosts(int page, int size, Long userId, FriendsPostRequestDto dto) {
        PageRequest pageable = createPageable(page, size);
        List<Long> friendIds = friendRepository.findFriendIdsByUserId(userId, dto.getFriendId());
        LocalDate toAt = dto.getToAt() == null ? null : dto.getToAt().plusDays(1);
        Page<Post> friendsPostsPage = friendPostRepository.findAllByUserId(pageable, friendIds, dto.getFromAt(), toAt);
        return new PaginationResponse<>(friendsPostsPage.map(FriendsPostResponseDto::new));
    }

    private User findUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new UserNotFoundException(userId));
        return user;
    }

    private PageRequest createPageable(int page, int size) {
        int enablePage = (page > 0) ? page - 1 : 0;
        return PageRequest.of(enablePage, size, Sort.by("updatedAt").descending());
    }
}
