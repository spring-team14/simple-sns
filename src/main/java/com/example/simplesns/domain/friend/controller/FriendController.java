package com.example.simplesns.domain.friend.controller;

import com.example.simplesns.common.dto.PaginationResponse;
import com.example.simplesns.domain.friend.dto.request.FriendRequestDto;
import com.example.simplesns.domain.friend.dto.request.ReqFriendRequestDto;
import com.example.simplesns.domain.friend.dto.request.SignOffFriendRequestDto;
import com.example.simplesns.domain.friend.dto.request.createFriendReqRequestDto;
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
    @PostMapping("/request")
    public ResponseEntity<createFriendReqResponseDto> createFriendReq(@RequestBody createFriendReqRequestDto dto) {
        return ResponseEntity.ok(friendService.createFriendReq(dto));
    }

    // 친구 요청 목록 조회
    @GetMapping("/request")
    public ResponseEntity<PaginationResponse<ReqFriendResponseDto>> findAllRequest(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody ReqFriendRequestDto dto) {
        return ResponseEntity.ok(friendService.findAllRequest(page, size, dto));
    }

    // 친구 요청 수락
    @PatchMapping("/request/{id}")
    public ResponseEntity<Void> signOffFriend(@PathVariable Long id,
                                              @RequestBody SignOffFriendRequestDto dto) {
        friendService.signOffFriend(id, dto);
        return ResponseEntity.ok().build();
    }

    // 친구 목록 조회
    @GetMapping
    public ResponseEntity<PaginationResponse<FriendResponseDto>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody FriendRequestDto dto) {
        return ResponseEntity.ok(friendService.findAll(page, size, dto));
    }

    // 친구 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id) {
        friendService.deleteFriend(id);
        return ResponseEntity.ok().build();
    }

    // TODO 친구 뉴스피드 조회
}
