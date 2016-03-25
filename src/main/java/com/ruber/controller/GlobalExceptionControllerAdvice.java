package com.ruber.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruber.exception.BadRequestBackendException;
import com.ruber.exception.ErrorCodes;
import com.ruber.exception.ForbiddenBackendException;
import com.ruber.exception.UnauthorisedBackendException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {
    @ExceptionHandler(UnauthorisedBackendException.class)
    @ResponseBody
    public ObjectNode handleUnauthorisedBackendExceptions(UnauthorisedBackendException ex, HttpServletResponse response) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("code", ex.getCode());
        node.put("message", ex.getMessage());

        response.setStatus(UNAUTHORIZED.value());

        return node;
    }

    @ExceptionHandler(BadRequestBackendException.class)
    @ResponseBody
    public ObjectNode handleBadRequestBackendExceptions(BadRequestBackendException ex, HttpServletResponse response) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("code", ex.getCode());
        node.put("message", ex.getMessage());

        response.setStatus(BAD_REQUEST.value());

        return node;
    }

    @ExceptionHandler(ForbiddenBackendException.class)
    @ResponseBody
    public ObjectNode handleForbiddenBackendExceptions(ForbiddenBackendException ex, HttpServletResponse response) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("code", ex.getCode());
        node.put("message", ex.getMessage());

        response.setStatus(FORBIDDEN.value());

        return node;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ObjectNode handleForbiddenBackendExceptions(Exception ex, HttpServletResponse response) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("code", ErrorCodes.INTERNAL_ERROR.getCode());
        node.put("message", ex.getMessage());

        response.setStatus(INTERNAL_SERVER_ERROR.value());

        return node;
    }
}