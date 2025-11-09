package com.farm2pot.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * packageName    : com.farm2pot.common.response
 * author         : TAEJIN
 * date           : 2025-10-06
 * description    : 에러 Response
 */
@Getter
@Builder
public class ErrorResponse {

    private final boolean success;
    private final String errorCode;
    private final String message;
    private final int  status;
    private final LocalDateTime timestamp;
    private final String path;

    public static ErrorResponse of(String errorCode, String message, int status, String path) {
        return ErrorResponse.builder()
                .success(false)
                .errorCode(errorCode)
                .message(message)
                .status(status)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }
}
