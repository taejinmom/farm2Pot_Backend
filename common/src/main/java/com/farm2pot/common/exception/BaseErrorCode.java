package com.farm2pot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * packageName    : com.farm2pot.common.exception
 * author         : TAEJIN
 * date           : 2025-10-06
 * description    :
 */
public interface BaseErrorCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}