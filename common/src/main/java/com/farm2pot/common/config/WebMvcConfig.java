package com.farm2pot.common.config;

import com.farm2pot.common.resolver.PageRequestArgumentResolver;
import com.farm2pot.common.resolver.SortRequestArgumentResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Spring Web MVC 설정
 * WebFlux 애플리케이션(Gateway)에서는 로드되지 않음
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageRequestArgumentResolver());
        resolvers.add(new SortRequestArgumentResolver());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);  // ISO-8601 형식 사용
        registrar.registerFormatters(registry);
    }
}
