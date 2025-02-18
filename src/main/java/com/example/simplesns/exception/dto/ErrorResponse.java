package com.example.simplesns.exception.dto;

import com.example.simplesns.exception.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return new ErrorResponse(
                errorCode.getHttpStatus().value(),
                message != null ? message : errorCode.getMessage(),
                LocalDateTime.now()
        );
    }
}
