package com.farm2pot.scription.utils.exception;

import com.farm2pot.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * packageName    : com.farm2pot.common.exception
 * author         : TAEJIN
 * date           : 2025-10-06
 * description    : UserService - Error 메세지 추가할거있으면 추가
 */
@Getter
@AllArgsConstructor
public enum SubscriptionErrorCode implements BaseErrorCode {
    /* Subscription 관련 */
    NOT_FOUND_ACTIVE_SUBSCRIPTION(HttpStatus.NOT_FOUND, "SUBS_001", "현재 활성 구독이 없습니다."),
    SUBSCRIPTION_ALREADY_EXPIRED(HttpStatus.CONFLICT, "SUBS_002", "이미 종료된 구독입니다."),
    NOT_FOUND_ACTIVE_SUBSCRIPTION_HISTORY(HttpStatus.NOT_FOUND, "SUBS_003", "현재 활성 구독이 없습니다.2"),
    INVALID_SUBSCRIPTION_PERIOD(HttpStatus.NOT_FOUND, "SUBS_004", "현재 활성 구독이 없습니다.3");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    public HttpStatus getStatus() { return httpStatus; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
}