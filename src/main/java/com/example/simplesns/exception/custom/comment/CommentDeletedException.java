package com.example.simplesns.exception.custom.comment;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.custom.BaseException;

public class CommentDeletedException extends BaseException {
    public CommentDeletedException(Long id) {
        super(ErrorCode.COMMENT_DELETED, "삭제된 댓글입니다. id = " + id);
    }
}
