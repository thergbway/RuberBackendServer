package com.ruber.controller.support;

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
    public ExceptionWrapper handleUnauthorisedBackendExceptions(UnauthorisedBackendException ex, HttpServletResponse response) {
        response.setStatus(UNAUTHORIZED.value());

        return new ExceptionWrapper(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestBackendException.class)
    @ResponseBody
    public ExceptionWrapper handleBadRequestBackendExceptions(BadRequestBackendException ex, HttpServletResponse response) {
        response.setStatus(BAD_REQUEST.value());

        return new ExceptionWrapper(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(ForbiddenBackendException.class)
    @ResponseBody
    public ExceptionWrapper handleForbiddenBackendExceptions(ForbiddenBackendException ex, HttpServletResponse response) {
        response.setStatus(FORBIDDEN.value());

        return new ExceptionWrapper(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ExceptionWrapper handleForbiddenBackendExceptions(Exception ex, HttpServletResponse response) {
        ex.printStackTrace();

        response.setStatus(INTERNAL_SERVER_ERROR.value());

        return new ExceptionWrapper(ErrorCodes.INTERNAL_ERROR.getCode(), ex.getMessage());
    }
}