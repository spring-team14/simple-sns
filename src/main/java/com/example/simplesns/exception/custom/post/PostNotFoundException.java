package com.example.simplesns.exception.custom.post;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class PostNotFoundException extends BaseException {
    public PostNotFoundException(Long id) {
        super(ErrorCode.POST_NOT_FOUND_BY_ID, "해당 ID의 게시글을 찾을 수 없습니다. id = " + id);
    }
}
