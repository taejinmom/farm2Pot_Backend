package com.farm2pot.common.exception;

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
public enum UserErrorCode implements BaseErrorCode {
    /* USER 관련 */
    /* 인증 / 로그인 관련 */
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_001", "아이디 또는 비밀번호가 잘못되었습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_003", "유효하지 않은 토큰입니다."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "AUTH_004", "접근 권한이 없습니다."),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "AUTH_005", "로그인이 필요합니다."),

    /* 회원가입 관련 */
    DUPLICATE_LOGIN_ID(HttpStatus.CONFLICT, "USER_001", "이미 사용 중인 아이디입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER_002", "이미 등록된 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_003", "해당 사용자를 찾을 수 없습니다."),

    /* 사용자 관련 */
    INVALID_PASASWORD(HttpStatus.UNAUTHORIZED, "USER_004", "비밀번호가 올바르지 않습니다."),
    /* 시스템 관련 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYS_001", "서버 내부 오류가 발생했습니다."),

    /* USER_ADDRESS 관련 */
    NOT_FOUND_ADDRESS(HttpStatus.NOT_FOUND,"ADDRESS_001","배송지를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }
}