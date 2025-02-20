package com.example.simplesns.exception.custom.friend;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class FriendStatusNotWaitException extends BaseException {
    public FriendStatusNotWaitException() {
        super(ErrorCode.FRIEND_STATUS_NOT_WAIT);
    }
}
