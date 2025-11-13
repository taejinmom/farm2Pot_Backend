package com.farm2pot.common.http.response;

import com.farm2pot.common.exception.BaseErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

/**
 * 공통 API 응답 형태
 * Controller는 DTO만 반환하고, ResponseBodyAdvice가 자동으로 이 형태로 감싸줌
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        T data,
        String responseCode,
        String responseMessage
) {

    // 성공 응답 (데이터만)
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .responseCode(ApiResponseCode.SUCCESS.getCode())
                .responseMessage(ApiResponseCode.SUCCESS.getMessage())
                .build();
    }

    // 성공 응답 (데이터 + 커스텀 메시지)
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .data(data)
                .responseCode(ApiResponseCode.SUCCESS.getCode())
                .responseMessage(message)
                .build();
    }

    // 성공 응답 (ResponseCode 기반)
    public static <T> ApiResponse<T> of(T data, BaseErrorCode responseCode) {
        return ApiResponse.<T>builder()
                .data(data)
                .responseCode(responseCode.getCode())
                .responseMessage(responseCode.getMessage())
                .build();
    }

    // 성공 응답 (ResponseCode + 커스텀 메시지)
    public static <T> ApiResponse<T> of(T data, BaseErrorCode responseCode, String message) {
        return ApiResponse.<T>builder()
                .data(data)
                .responseCode(responseCode.getCode())
                .responseMessage(message)
                .build();
    }

    // 에러 응답 (BaseErrorCode 기반)
    public static <T> ApiResponse<T> error(BaseErrorCode errorCode) {
        return ApiResponse.<T>builder()
                .data(null)
                .responseCode(errorCode.getCode())
                .responseMessage(errorCode.getMessage())
                .build();
    }

    // 에러 응답 (BaseErrorCode + 커스텀 메시지)
    public static <T> ApiResponse<T> error(BaseErrorCode errorCode, String message) {
        return ApiResponse.<T>builder()
                .data(null)
                .responseCode(errorCode.getCode())
                .responseMessage(message)
                .build();
    }

    // 에러 응답 (BaseErrorCode + 메시지 + 데이터)
    public static <T> ApiResponse<T> error(BaseErrorCode errorCode, String message, T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .responseCode(errorCode.getCode())
                .responseMessage(message)
                .build();
    }

    // 에러 응답 (코드 + 메시지 직접 지정)
    public static <T> ApiResponse<T> error(String code, String message) {
        return ApiResponse.<T>builder()
                .data(null)
                .responseCode(code)
                .responseMessage(message)
                .build();
    }
}
