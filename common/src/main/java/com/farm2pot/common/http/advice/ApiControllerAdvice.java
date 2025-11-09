package com.farm2pot.common.http.advice;

import com.farm2pot.common.exception.BaseException;
import com.farm2pot.common.http.response.ApiResponse;
import com.farm2pot.common.http.response.ApiResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * 전역 예외 처리 핸들러
 */
@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    /**
     * BaseException 처리
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handleBaseException(BaseException e) {
        log.error("BaseException occurred: code={}, message={}", e.getCode(), e.getMessage(), e);

        ApiResponse<?> response = ApiResponse.error(
                e.getCode(),
                e.getMessage()
        );

        return ResponseEntity
                .status(e.getStatus())
                .body(response);
    }

    /**
     * Validation 예외 처리 (@Valid, @Validated)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation error occurred: {}", e.getMessage(), e);

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Map<String, String>> response = ApiResponse.error(
                ApiResponseCode.VALIDATION_ERROR,
                "유효성 검증에 실패했습니다",
                errors
        );

        return ResponseEntity
                .status(ApiResponseCode.VALIDATION_ERROR.getHttpStatus())
                .body(response);
    }

    /**
     * BindException 처리
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<?>> handleBindException(BindException e) {
        log.error("Binding error occurred: {}", e.getMessage(), e);

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Map<String, String>> response = ApiResponse.error(
                ApiResponseCode.VALIDATION_ERROR,
                "요청 데이터 바인딩에 실패했습니다",
                errors
        );

        return ResponseEntity
                .status(ApiResponseCode.VALIDATION_ERROR.getHttpStatus())
                .body(response);
    }

    /**
     * 필수 파라미터 누락 처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("Missing parameter error: {}", e.getMessage(), e);

        ApiResponse<?> response = ApiResponse.error(
                ApiResponseCode.BAD_REQUEST,
                String.format("필수 파라미터가 누락되었습니다: %s", e.getParameterName())
        );

        return ResponseEntity
                .status(ApiResponseCode.BAD_REQUEST.getHttpStatus())
                .body(response);
    }

    /**
     * 타입 불일치 처리
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("Type mismatch error: {}", e.getMessage(), e);

        ApiResponse<?> response = ApiResponse.error(
                ApiResponseCode.BAD_REQUEST,
                String.format("파라미터 타입이 올바르지 않습니다: %s", e.getName())
        );

        return ResponseEntity
                .status(ApiResponseCode.BAD_REQUEST.getHttpStatus())
                .body(response);
    }

    /**
     * HTTP 메시지 읽기 실패 처리
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("Message not readable error: {}", e.getMessage(), e);

        ApiResponse<?> response = ApiResponse.error(
                ApiResponseCode.BAD_REQUEST,
                "요청 본문을 읽을 수 없습니다"
        );

        return ResponseEntity
                .status(ApiResponseCode.BAD_REQUEST.getHttpStatus())
                .body(response);
    }

    /**
     * 지원하지 않는 HTTP 메서드 처리
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Method not supported error: {}", e.getMessage(), e);

        ApiResponse<?> response = ApiResponse.error(
                ApiResponseCode.METHOD_NOT_ALLOWED,
                String.format("지원하지 않는 HTTP 메서드입니다: %s", e.getMethod())
        );

        return ResponseEntity
                .status(ApiResponseCode.METHOD_NOT_ALLOWED.getHttpStatus())
                .body(response);
    }

    /**
     * 핸들러를 찾을 수 없는 경우 처리
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("No handler found error: {}", e.getMessage(), e);

        ApiResponse<?> response = ApiResponse.error(
                ApiResponseCode.NOT_FOUND,
                "요청한 리소스를 찾을 수 없습니다"
        );

        return ResponseEntity
                .status(ApiResponseCode.NOT_FOUND.getHttpStatus())
                .body(response);
    }

    /**
     * 그 외 모든 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Unexpected error occurred: {}", e.getMessage(), e);

        ApiResponse<?> response = ApiResponse.error(
                ApiResponseCode.INTERNAL_SERVER_ERROR,
                "서버 내부 오류가 발생했습니다"
        );

        return ResponseEntity
                .status(ApiResponseCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(response);
    }
}
