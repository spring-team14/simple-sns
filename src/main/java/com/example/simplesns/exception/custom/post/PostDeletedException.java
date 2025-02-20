package com.example.simplesns.exception.custom.post;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class PostDeletedException extends BaseException {
    public PostDeletedException(Long id) {
        super(ErrorCode.POST_DELETED, "삭제된 게시글입니다. id = " + id);
    }
}
