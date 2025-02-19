package com.example.simplesns.exception.custom.user;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(Long id) {
        super(ErrorCode.USER_NOT_FOUND_BY_ID, "해당 ID의 유저를 찾을 수 없습니다. id = " + id);
    }
}
