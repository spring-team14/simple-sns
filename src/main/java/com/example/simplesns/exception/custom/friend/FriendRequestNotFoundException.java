package com.example.simplesns.exception.custom.friend;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class FriendRequestNotFoundException extends BaseException {
    public FriendRequestNotFoundException() {
        super(ErrorCode.FRIEND_REQUEST_NOT_FOUND);
    }
}
