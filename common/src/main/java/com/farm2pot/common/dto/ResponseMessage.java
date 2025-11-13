//package com.farm2pot.common.dto;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.time.LocalDateTime;
//
///**
// * packageName    : com.farm2pot.common.response
// * author         : TAEJIN
// * date           : 2025-11-08
// * description    : 공통 API 응답 포맷 Response용 Wrapper 클래스
// */
//@Getter
//@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class ResponseMessage<T> {
//
//    private final boolean success;
//    private final String message;
//    private final T data;
//    private final LocalDateTime timestamp;
//    private final String path;   // 요청 URL (예외 처리 시 사용)
//    private final Integer status; // HTTP 상태 코드
//
//    // ✅ 성공 응답
//    public static <T> ResponseMessage<T> success(String message, T data) {
//        return ResponseMessage.<T>builder()
//                .success(true)
//                .message(message)
//                .data(data)
//                .timestamp(LocalDateTime.now())
//                .status(200)
//                .build();
//    }
//
//    // ✅ 단순 성공 (데이터 없이)
//    public static <T> ResponseMessage<T> success(String message) {
//        return ResponseMessage.<T>builder()
//                .success(true)
//                .message(message)
//                .timestamp(LocalDateTime.now())
//                .status(200)
//                .build();
//    }
//
//    // ✅ 실패 응답
//    public static <T> ResponseMessage<T> fail(String message, int status, String path) {
//        return ResponseMessage.<T>builder()
//                .success(false)
//                .message(message)
//                .timestamp(LocalDateTime.now())
//                .status(status)
//                .path(path)
//                .build();
//    }
//}