package com.example.simplesns.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    INVALID_NEWPASSWORD(HttpStatus.UNAUTHORIZED, "새 비밀번호는 기존 비밀번호와 다르게 설정해야 합니다."),

    INVALID_EMAIL(HttpStatus.UNAUTHORIZED, "이메일이 일치하지 않습니다."),

    USER_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "해당 ID의 멤버를 찾을 수 없습니다."),

    USER_NOT_FOUND_BY_EMAIL(HttpStatus.NOT_FOUND, "해당 이메일의 멤버를 찾을 수 없습니다."),

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    COMMENT_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "해당 ID의 댓글을 찾을 수 없습니다."),

    COMMENT_DELETED(HttpStatus.NOT_FOUND, "삭제된 댓글입니다."),

    FRIEND_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "친구 요청 정보를 찾을 수 없습니다."),

    FRIEND_REQUEST_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "친구 요청 데이터가 이미 존재합니다."),

    FRIEND_NOT_FOUND(HttpStatus.NOT_FOUND, "친구 정보를 찾을 수 없습니다."),

    FRIEND_STATUS_NOT_WAIT(HttpStatus.BAD_REQUEST, "대기 상태의 요청만 처리할 수 있습니다."),

    FRIEND_REQUEST_TO_SELF(HttpStatus.BAD_REQUEST, "본인을 친구 추가할 수 없습니다."),

    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),

    UNAUTHORIZED(HttpStatus.FORBIDDEN, "이 작업을 수행할 권한이 없습니다."),

    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다."),

    POST_NOT_FOUND_BY_ID(HttpStatus.NOT_FOUND, "해당 ID의 게시글을 찾을 수 없습니다."),

    POST_DELETED(HttpStatus.NOT_FOUND, "삭제된 게시글입니다."),

    SELF_LIKE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "본인 게시글/댓글에는 좋아요를 누를 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
