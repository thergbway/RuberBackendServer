package com.ruber.controller.support;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalModelAttributeControllerAdvice {
    @ModelAttribute("user_id")
    public Integer getUserId(HttpServletRequest request) {
        return ((Integer) request.getAttribute("user_id"));
    }
}
