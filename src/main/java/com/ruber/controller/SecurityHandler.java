package com.ruber.controller;

import com.ruber.dao.UserDAO;
import com.ruber.exception.InvalidAccessTokenException;
import com.ruber.exception.NoAccessTokenException;
import com.ruber.service.RuberTokensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("securityHandler")
@Transactional//fixme
public class SecurityHandler extends HandlerInterceptorAdapter {
    @Autowired
    private RuberTokensService ruberTokensService;

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Token");
        if (token == null)
            throw new NoAccessTokenException();
        else if (!ruberTokensService.isValidToken(token))
            throw new InvalidAccessTokenException();

        request.setAttribute("user_id", userDAO.getByRuberToken(token).getId());
        return true;
    }
}