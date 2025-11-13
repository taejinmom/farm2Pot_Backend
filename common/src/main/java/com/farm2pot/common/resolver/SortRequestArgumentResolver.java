package com.farm2pot.common.resolver;

import com.farm2pot.common.http.request.CustomSortRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * CustomSortRequest를 위한 ArgumentResolver
 * 클라이언트의 sort 파라미터를 자동으로 CustomSortRequest로 변환
 */
public class SortRequestArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(CustomSortRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // sort 파라미터 (다중 값 지원)
        String[] sort = webRequest.getParameterValues("sort");

        return CustomSortRequest.of(sort);
    }
}
