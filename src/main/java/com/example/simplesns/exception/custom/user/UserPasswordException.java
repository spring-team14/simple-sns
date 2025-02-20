package com.example.simplesns.exception.custom.user;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class UserPasswordException extends BaseException {
    public UserPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
