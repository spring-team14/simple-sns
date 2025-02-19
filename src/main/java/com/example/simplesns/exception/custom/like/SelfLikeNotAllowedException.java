package com.example.simplesns.exception.custom.like;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class SelfLikeNotAllowedException extends BaseException {
    public SelfLikeNotAllowedException(String message) {
        super(ErrorCode.SELF_LIKE_NOT_ALLOWED, message);
    }
}
