//package com.farm2pot.common.advice;
//
//import com.farm2pot.common.dto.ErrorResponse;
//import com.farm2pot.common.dto.ResponseMessage;
//import com.farm2pot.common.exception.BaseException;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
///**
// * packageName    : com.farm2pot.common.response
// * author         : TAEJIN
// * date           : 2025-11-08
// * description    : 전역 예외 핸들러 (공통 예외 응답 구조 유지)
// */
//@RestControllerAdvice(basePackages = "com.farm2pot")
//@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    public ResponseMessage<Object> handleGlobalException(Exception ex, WebRequest request) {
//        return ResponseMessage.fail(
//                ex.getMessage(),
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                request.getDescription(false)
//        );
//    }
//
//    @ExceptionHandler(BaseException.class)
//    public ErrorResponse handleBaseException(BaseException ex, HttpServletRequest request) {
//        var errorCode = ex.getErrorCode();
//        return ErrorResponse.of(
//                errorCode.getCode(),
//                errorCode.getMessage(),
//                errorCode.getStatus().value(),
//                request.getRequestURI()
//        );
//    }
//
//    // ✅ IllegalArgumentException, RuntimeException 등도 따로 처리 가능
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseMessage<Object> handleBadRequest(IllegalArgumentException ex, WebRequest request) {
//        return ResponseMessage.fail(
//                ex.getMessage(),
//                HttpStatus.BAD_REQUEST.value(),
//                request.getDescription(false)
//        );
//    }
//}