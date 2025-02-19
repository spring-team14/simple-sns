package com.example.simplesns.exception.custom.friend;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class FriendRequestToSelfException extends BaseException {
    public FriendRequestToSelfException(Long id) {
        super(ErrorCode.FRIEND_REQUEST_TO_SELF, "본인을 친구 추가할 수 없습니다. id: " + id);
    }
}
