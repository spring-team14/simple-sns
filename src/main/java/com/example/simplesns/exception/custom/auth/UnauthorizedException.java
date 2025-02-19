package com.example.simplesns.exception.custom.auth;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }
}
