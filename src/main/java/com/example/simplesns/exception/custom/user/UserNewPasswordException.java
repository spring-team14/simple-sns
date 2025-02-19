package com.example.simplesns.exception.custom.user;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class UserNewPasswordException extends BaseException {
    public UserNewPasswordException() {
        super(ErrorCode.INVALID_NEWPASSWORD);
    }
}
