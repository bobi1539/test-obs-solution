package com.test.obs.solution.controller;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.dto.response.BaseResponse;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.test.obs.solution.controller")
public class CustomResponseController implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(
            @NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {
        if (body instanceof InputStreamResource) {
            return body;
        }
        if (returnType.getContainingClass().isAnnotationPresent(RestController.class)) {
            return buildSuccessResponse(body);
        }
        return body;
    }

    public static <T> BaseResponse<T> buildSuccessResponse(T object) {
        return BaseResponse.<T>builder()
                .code(GlobalMessage.SUCCESS.httpStatus.value())
                .message(GlobalMessage.SUCCESS.message)
                .data(object)
                .build();
    }
}
