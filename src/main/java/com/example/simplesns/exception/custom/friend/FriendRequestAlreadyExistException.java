package com.example.simplesns.exception.custom.friend;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class FriendRequestAlreadyExistException extends BaseException {
    public FriendRequestAlreadyExistException() {
        super(ErrorCode.FRIEND_REQUEST_ALREADY_EXIST);
    }
}
