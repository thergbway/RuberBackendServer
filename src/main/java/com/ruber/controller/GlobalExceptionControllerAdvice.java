package com.ruber.controller;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruber.exception.BackendException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(BackendException.class)
    @ResponseBody
    public ObjectNode handleAllExceptions(BackendException ex) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("code", ex.getCode());
        node.put("message", ex.getMessage());

        return node;
    }
}