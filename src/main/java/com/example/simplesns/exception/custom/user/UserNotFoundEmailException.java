package com.example.simplesns.exception.custom.user;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class UserNotFoundEmailException extends BaseException {
    public UserNotFoundEmailException() {
        super(ErrorCode.INVALID_EMAIL);
    }
}
