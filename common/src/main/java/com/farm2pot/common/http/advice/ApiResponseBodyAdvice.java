package com.farm2pot.common.http.advice;

import com.farm2pot.common.http.response.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Controller의 응답을 자동으로 ApiResponse로 감싸주는 Advice
 * Controller에서 DTO만 반환하면 자동으로 공통 응답 형태로 변환
 */
@RestControllerAdvice(basePackages = "com.farm2pot")
public class ApiResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 이미 ApiResponse로 감싸진 경우는 제외
        return !returnType.getParameterType().equals(ApiResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // 이미 ApiResponse인 경우 그대로 반환
        if (body instanceof ApiResponse) {
            return body;
        }

        // DTO를 ApiResponse로 감싸서 반환
        return ApiResponse.success(body);
    }
}
