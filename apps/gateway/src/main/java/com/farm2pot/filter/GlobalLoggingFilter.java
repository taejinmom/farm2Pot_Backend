package com.farm2pot.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Gateway를 통과하는 모든 요청/응답을 로깅하는 필터
 */
@Slf4j
@Component
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 요청 정보 로깅
        log.info("=== Gateway Request ===");
        log.info("Method: {}", request.getMethod());
        log.info("Path: {}", request.getPath());
        log.info("URI: {}", request.getURI());
        log.info("Headers: {}", request.getHeaders());

        long startTime = System.currentTimeMillis();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            // 응답 정보 로깅
            log.info("=== Gateway Response ===");
            log.info("Status Code: {}", response.getStatusCode());
            log.info("Duration: {}ms", duration);
            log.info("======================");
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;  // 가장 나중에 실행
    }
}
