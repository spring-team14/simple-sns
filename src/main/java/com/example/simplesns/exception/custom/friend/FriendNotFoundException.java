package com.example.simplesns.exception.custom.friend;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class FriendNotFoundException extends BaseException {
    public FriendNotFoundException() {
        super(ErrorCode.FRIEND_NOT_FOUND);
    }
}
