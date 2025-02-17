package com.example.simplesns.domain.friend.controller;

import com.example.simplesns.domain.friend.dto.common.PaginationResponse;
import com.example.simplesns.domain.friend.dto.request.*;
import com.example.simplesns.domain.friend.dto.response.FriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.ReqFriendResponseDto;
import com.example.simplesns.domain.friend.dto.response.createFriendReqResponseDto;
import com.example.simplesns.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 친구 등록
    @PostMapping
    public ResponseEntity<createFriendReqResponseDto> createFriendReq(@RequestBody createFriendReqRequestDto dto) {
        return ResponseEntity.ok(friendService.createFriendReq(dto));
    }

    // 친구 목록 조회
    @GetMapping
    public ResponseEntity<PaginationResponse<FriendResponseDto>> findAll(@RequestBody FriendRequestDto dto) {
        return ResponseEntity.ok(friendService.findAll(dto));
    }

    // 친구 요청 목록 조회
    @GetMapping("/request")
    public ResponseEntity<PaginationResponse<ReqFriendResponseDto>> findAllRequest(@RequestBody ReqFriendRequestDto dto) {
        return ResponseEntity.ok(friendService.findAllRequest(dto));
    }

    // 친구 요청 수락
    @PatchMapping("/request/{toId}")
    public ResponseEntity<Void> signOffFriend(@PathVariable Long toId,
                                              @RequestBody SignOffFriendRequestDto dto) {
        friendService.signOffFriend(toId, dto);
        return ResponseEntity.ok().build();
    }

    // 친구 삭제
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long friendId,
                                             @RequestBody DeleteFriendRequestDto dto) {
        friendService.deleteFriend(friendId, dto);
        return ResponseEntity.ok().build();
    }

    // TODO 친구 뉴스피드 조회
}
