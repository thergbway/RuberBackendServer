package com.ruber.util;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * This class resolves Spring MVC issue to support PUT multipart methods
 */
public class ExtendedMultipartResolver extends CommonsMultipartResolver {
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        if (request != null) {
            String httpMethod = request.getMethod();
            String contentType = request.getContentType();
            return (contentType != null && contentType.toLowerCase().startsWith("multipart") &&
                ("POST".equalsIgnoreCase(httpMethod) || "PUT".equalsIgnoreCase(httpMethod)));
        } else {
            return false;
        }
    }
}
