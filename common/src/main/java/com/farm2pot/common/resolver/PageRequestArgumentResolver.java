package com.farm2pot.common.resolver;

import com.farm2pot.common.http.request.CustomPageRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * CustomPageRequest를 위한 ArgumentResolver
 * 클라이언트의 page, pageSize, sort 파라미터를 자동으로 CustomPageRequest로 변환
 */
public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(CustomPageRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String pageParam = webRequest.getParameter("page");
        String pageSizeParam = webRequest.getParameter("pageSize");

        int page = pageParam != null ? Integer.parseInt(pageParam) : DEFAULT_PAGE;
        int pageSize = pageSizeParam != null ? Integer.parseInt(pageSizeParam) : DEFAULT_PAGE_SIZE;

        // sort 파라미터 (다중 값 지원)
        String[] sort = webRequest.getParameterValues("sort");

        return CustomPageRequest.of(page, pageSize, sort);
    }
}
