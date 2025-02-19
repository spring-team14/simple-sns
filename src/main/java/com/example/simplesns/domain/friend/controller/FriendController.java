package com.example.simplesns.domain.friend.controller;

import com.example.simplesns.common.consts.Const;
import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.friend.dto.request.*;
import com.example.simplesns.domain.friend.dto.response.FriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.FriendsPostResponseDto;
import com.example.simplesns.domain.friend.dto.response.ReqFriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.createFriendReqResponseDto;
import com.example.simplesns.domain.friend.service.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 친구 등록
    @PostMapping("/request")
    public ResponseEntity<createFriendReqResponseDto> createFriendReq(@SessionAttribute(name = Const.LOGIN_USER) Long userId,
                                                                      @Valid @RequestBody createFriendReqRequestDto dto) {
        return ResponseEntity.ok(friendService.createFriendReq(userId, dto));
    }

    // 친구 요청 목록 조회
    @GetMapping("/request")
    public ResponseEntity<PaginationResponse<ReqFriendResponseDto>> findAllRequest(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody ReqFriendRequestDto dto) {
        return ResponseEntity.ok(friendService.findAllRequest(page, size, userId, dto));
    }

    // 친구 요청 수락
    @PatchMapping("/request/{id}")
    public ResponseEntity<Void> signOffFriend(@PathVariable Long id,
                                              @SessionAttribute(name = Const.LOGIN_USER) Long userId,
                                              @Valid @RequestBody SignOffFriendRequestDto dto) {
        friendService.signOffFriend(id, userId, dto);
        return ResponseEntity.ok().build();
    }

    // 친구 목록 조회
    @GetMapping
    public ResponseEntity<PaginationResponse<FriendResponseDto>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @RequestBody FindFriendRequestDto dto) {
        return ResponseEntity.ok(friendService.findAll(page, size, userId, dto));
    }

    // 친구 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id, @SessionAttribute(name = Const.LOGIN_USER) Long userId) {
        friendService.deleteFriend(id, userId);
        return ResponseEntity.ok().build();
    }

    // 친구 뉴스피드 조회
    @GetMapping("/posts")
    public ResponseEntity<PaginationResponse<FriendsPostResponseDto>> findFriendsPosts(@RequestParam(defaultValue = "1") int page,
                                                                                       @RequestParam(defaultValue = "10") int size,
                                                                                       @SessionAttribute(name = Const.LOGIN_USER) Long userId,
                                                                                       @Valid @RequestBody FriendsPostRequestDto dto) {
        return ResponseEntity.ok(friendService.findFriendsPosts(page, size, userId, dto));
    }
}
