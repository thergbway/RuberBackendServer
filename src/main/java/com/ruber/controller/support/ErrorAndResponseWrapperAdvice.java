package com.ruber.controller.support;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static java.util.Objects.isNull;

@ControllerAdvice
public class ErrorAndResponseWrapperAdvice implements ResponseBodyAdvice {
    private JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !converterType.equals(ByteArrayHttpMessageConverter.class);//support for File downloading method
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (isNull(body))
            return null;

        if (body instanceof ExceptionWrapper)
            return jsonNodeFactory.objectNode().putPOJO("error", body);
        else
            return jsonNodeFactory.objectNode().putPOJO("response", body);
    }
}