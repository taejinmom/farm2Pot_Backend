package com.farm2pot.common.exception;

import org.springframework.http.HttpStatus;

/**
 * packageName    : com.farm2pot.jwt.exception
 * author         : TAEJIN
 * date           : 2025-10-11
 * description    :
 */
public enum GatewayErrorCode  {
    // JWT 관련 오류
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "GATE_001", "토큰이 만료되었거나 유효하지 않습니다."),
    TOKEN_BLACKLISTED(HttpStatus.UNAUTHORIZED, "GATE_002", "로그아웃되었거나 블랙리스트에 등록된 토큰입니다."),
    MISSING_AUTH_HEADER(HttpStatus.UNAUTHORIZED, "GATE_003", "Authorization 헤더가 존재하지 않습니다."),

    // 접근 권한 관련
    FORBIDDEN(HttpStatus.FORBIDDEN, "GATE_004", "접근 권한이 없습니다."),

    // 기타 공통 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GATE_999", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    GatewayErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}