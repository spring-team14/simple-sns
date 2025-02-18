package com.example.simplesns.exception.custom;

import com.example.simplesns.exception.code.ErrorCode;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
