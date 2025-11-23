package com.farm2pot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Gateway 라우팅 설정을 위한 Properties 클래스
 * application.yml의 routes 설정을 읽어옴
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "routes")
public class RouteProperties {

    private Map<String, RouteConfig> services;

    @Getter
    @Setter
    public static class RouteConfig {
        private String url;   // 대상 서비스 URL
        private String path;  // 라우팅 경로 패턴
    }

    // yml에서 직접 읽을 수 있도록 각 서비스별 설정
    private RouteConfig core;
    private RouteConfig user;
    private RouteConfig admin;
    private RouteConfig subscription;
}
