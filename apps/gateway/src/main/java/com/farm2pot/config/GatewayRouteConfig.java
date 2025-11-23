package com.farm2pot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Cloud Gateway 라우팅 설정
 * 프론트엔드가 하나의 게이트웨이 포트로 요청하면 각 서비스로 라우팅
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class GatewayRouteConfig {

    private final RouteProperties routeProperties;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        log.info("=== Gateway Routes Configuration ===");

        RouteLocatorBuilder.Builder routes = builder.routes();

        // Core Service 라우팅
        if (routeProperties.getCore() != null) {
            String coreUrl = routeProperties.getCore().getUrl();
            String corePath = routeProperties.getCore().getPath();
            log.info("Core Service: {} -> {}", corePath, coreUrl);

            routes.route("core-service", r -> r
                    .path(corePath)
                    .filters(f -> f.stripPrefix(2))  // /api/core 제거
                    .uri(coreUrl)
            );
        }

        // User Service 라우팅
        if (routeProperties.getUser() != null) {
            String userUrl = routeProperties.getUser().getUrl();
            String userPath = routeProperties.getUser().getPath();
            log.info("User Service: {} -> {}", userPath, userUrl);

            routes.route("user-service", r -> r
                    .path(userPath)
                    .filters(f -> f.stripPrefix(2))  // /api/user 제거
                    .uri(userUrl)
            );
        }

        // Admin Service 라우팅
        if (routeProperties.getAdmin() != null) {
            String adminUrl = routeProperties.getAdmin().getUrl();
            String adminPath = routeProperties.getAdmin().getPath();
            log.info("Admin Service: {} -> {}", adminPath, adminUrl);

            routes.route("admin-service", r -> r
                    .path(adminPath)
                    .filters(f -> f.stripPrefix(2))  // /api/admin 제거
                    .uri(adminUrl)
            );
        }

        // Subscription Service 라우팅
        if (routeProperties.getSubscription() != null) {
            String subsUrl = routeProperties.getSubscription().getUrl();
            String subsPath = routeProperties.getSubscription().getPath();
            log.info("Subscription Service: {} -> {}", subsPath, subsUrl);

            routes.route("subscription-service", r -> r
                    .path(subsPath)
                    .filters(f -> f.stripPrefix(2))  // /api/subs 제거
                    .uri(subsUrl)
            );
        }

        log.info("=================================");
        return routes.build();
    }
}
