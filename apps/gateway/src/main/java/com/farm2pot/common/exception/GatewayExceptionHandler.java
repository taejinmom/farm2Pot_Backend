//package com.farm2pot.common.exception;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpStatus;
//
//
//import org.springframework.core.annotation.Order;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebExceptionHandler;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDateTime;
//
///**
// * packageName    : com.farm2pot.common.exception
// * author         : TAEJIN
// * date           : 2025-10-11
// * description    : gateway - ExceptionHandler (ServerHttpRequest)
// */
//@Component
//@Order(-2) // 우선순위
//public class GatewayExceptionHandler implements WebExceptionHandler {
//
//    @Override
//    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
//        GatewayErrorResponse<Object> response;
//
//        if (ex instanceof GatewayException ge) {
//            response = GatewayErrorResponse.error(
//                    ge.getErrorCode().getMessage(),
//                    ge.getStatus()
//            );
//        } else {
//            response = GatewayErrorResponse.error("Internal Server Error", 500);
//        }
//
//        byte[] bytes;
//        try {
//            bytes = new ObjectMapper().writeValueAsBytes(response);
//        } catch (Exception e) {
//            bytes = "{\"success\":false,\"message\":\"Serialization Error\",\"status\":500}".getBytes();
//        }
//
//        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
//        exchange.getResponse().setStatusCode(HttpStatus.valueOf(response.getStatus()));
//
//        return exchange.getResponse()
//                .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
//    }
//}