package com.ruber.service;

import com.ruber.controller.dto.AuthRequest;
import com.ruber.controller.dto.AuthResponse;
import com.ruber.dao.RuberTokenDAO;
import com.ruber.dao.entity.RuberToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private RuberTokenDAO ruberTokenDAO;

    public boolean checkAccessToken(String ruberAccessToken) {
        return ruberTokenDAO.getByValue(ruberAccessToken) != null;
    }

    public String getVkAccessToken(String ruberAccessToken) {
        RuberToken ruberToken = ruberTokenDAO.getByValue(ruberAccessToken);

        return ruberToken.getVkTokens().toArray(new String[0])[0];
    }

    public Integer getUserId(String ruberToken) {
        return ruberTokenDAO.getByValue(ruberToken).getUserId();
    }

    @Transactional(
        propagation = Propagation.REQUIRES_NEW,
        isolation = Isolation.SERIALIZABLE
    )
    public AuthResponse authenticate(AuthRequest req) {
        Integer userId = req.getVk_user_id();
        String vkToken = req.getVk_access_token();

        if (ruberTokenDAO.read(userId) == null) {
            RuberToken ruberToken = new RuberToken(userId,
                System.currentTimeMillis() + "_" + userId, Collections.singleton(vkToken));
            ruberTokenDAO.create(ruberToken);
            return new AuthResponse(ruberToken.getValue(), userId);
        } else {
            RuberToken ruberToken = ruberTokenDAO.read(userId);
            ruberToken.getVkTokens().add(vkToken);
            ruberTokenDAO.update(ruberToken);

            return new AuthResponse(ruberToken.getValue(), userId);
        }
    }
}