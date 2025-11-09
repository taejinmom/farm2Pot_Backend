package com.farm2pot.security.filter;

import com.farm2pot.common.exception.GatewayErrorCode;
import com.farm2pot.common.exception.GatewayException;

import com.farm2pot.security.config.SecurityProperties;
import com.farm2pot.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtProvider jwtProvider;
    private final ReactiveStringRedisTemplate redisTemplate;
    private final SecurityProperties securityProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        List<String> whiteList = securityProperties.getWhiteList();

        // 화이트리스트 예외처리
        boolean isWhitelisted = whiteList.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        if (isWhitelisted) {
            log.debug("[JWT FILTER] Skipped authentication for path: {}", path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("[JWT FILTER] Missing Authorization header");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return Mono.error(new GatewayException(GatewayErrorCode.MISSING_AUTH_HEADER));
        }

        String token = authHeader.substring(7).trim();

        return redisTemplate.hasKey("BL:" + token)
                .flatMap(isBlacklisted -> {
                    if (Boolean.TRUE.equals(isBlacklisted)) {
                        log.error("[JWT FILTER] Token is blacklisted: {}", token);
                        return Mono.error(new GatewayException(GatewayErrorCode.TOKEN_BLACKLISTED));
                    }

                    if (!jwtProvider.validateToken(token)) {
                        log.error("[JWT FILTER] Invalid token: {}", token);
                        return Mono.error(new GatewayException(GatewayErrorCode.INVALID_TOKEN));
                    }

                    String id = jwtProvider.getId(token);
                    List<String> roles = jwtProvider.getRoles(token);

                    // 인증 성공 → 헤더에 등록
                    exchange.getRequest().mutate()
                            .header("X-USER-ID", id)
                            .build();

                    log.info("[JWT FILTER] Authenticated Id: {} / Roles: {}", id, roles);

                    var authentication = new UsernamePasswordAuthenticationToken(
                            id, null,
                            roles.stream().map(SimpleGrantedAuthority::new).toList()
                    );

                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                });
    }
}