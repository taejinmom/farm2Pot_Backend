package com.farm2pot.common.advice;

import com.farm2pot.common.dto.ResponseMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * packageName    : com.farm2pot.common.response
 * author         : TAEJIN
 * date           : 2025-11-08
 * description    :
 */

@ControllerAdvice(basePackages = "com.farm2pot")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        // 이미 ResponseMessage 타입이면 그대로 반환
        if (body instanceof ResponseMessage) {
            return body;
        }

        // void 메서드 처리 (body == null)
        if (body == null) {
            return ResponseMessage.success("OK", null);
        }

        // 기본 success wrapper 적용
        return ResponseMessage.success("Success", body);
    }
}
