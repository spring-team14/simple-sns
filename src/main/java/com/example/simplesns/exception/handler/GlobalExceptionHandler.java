package com.example.simplesns.exception.handler;

import com.example.simplesns.exception.code.ErrorCode;
import com.example.simplesns.exception.dto.ErrorResponse;
import com.example.simplesns.exception.custom.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return ResponseEntity
                .status(ex.getErrorCode().getHttpStatus())
                .body(ErrorResponse.of(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity
                .status(ErrorCode.UNKNOWN.getHttpStatus())
                .body(ErrorResponse.of(ErrorCode.UNKNOWN, ErrorCode.UNKNOWN.getMessage()));
    }
}
