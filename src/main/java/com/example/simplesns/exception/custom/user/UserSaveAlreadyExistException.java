package com.example.simplesns.exception.custom.user;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class UserSaveAlreadyExistException extends BaseException {
    public UserSaveAlreadyExistException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
