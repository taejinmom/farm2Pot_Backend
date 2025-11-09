package com.farm2pot.common.exception;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
/**
 * packageName    : com.farm2pot.common.exception
 * author         : TAEJIN
 * date           : 2025-11-09
 * description    :
 */


@Getter
@Builder
public class GatewayErrorResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final int status;
    private final LocalDateTime timestamp;

    public static <T> GatewayErrorResponse<T> success(String message, T data) {
        return GatewayErrorResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .status(200)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> GatewayErrorResponse<T> error(String message, int status) {
        return GatewayErrorResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
