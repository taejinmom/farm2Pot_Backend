package com.farm2pot.common.http.response;

import com.farm2pot.common.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * API 응답 코드 enum
 */
@Getter
@RequiredArgsConstructor
public enum ApiResponseCode implements BaseErrorCode {

    // 성공 응답
    SUCCESS(HttpStatus.OK, "SUCCESS", "성공"),
    CREATED(HttpStatus.CREATED, "CREATED", "생성 성공"),

    // 클라이언트 에러
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "인증 실패"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "권한 없음"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "리소스를 찾을 수 없음"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "허용되지 않은 메서드"),
    CONFLICT(HttpStatus.CONFLICT, "CONFLICT", "충돌 발생"),

    // 서버 에러
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", "서비스 이용 불가"),

    // 비즈니스 에러
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "유효성 검증 실패"),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "DUPLICATE_RESOURCE", "중복된 리소스"),
    BUSINESS_ERROR(HttpStatus.BAD_REQUEST, "BUSINESS_ERROR", "비즈니스 로직 오류");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
