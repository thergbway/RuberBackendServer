package com.ruber.service;

import com.ruber.controller.dto.AuthRequest;
import com.ruber.controller.dto.AuthResponse;
import com.ruber.exception.InvalidExternalAppCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional//TODO
public class AuthService {
    @Autowired
    private ExternalAppCredentialsService externalAppCredentialsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private RuberTokensService ruberTokensService;

    @Transactional(
        propagation = Propagation.REQUIRES_NEW,
        isolation = Isolation.SERIALIZABLE
    )
    public AuthResponse authenticate(AuthRequest req) {
        if (!externalAppCredentialsService.checkCredentials(req.getRuber_app_id(), req.getRuber_app_secret()))
            throw new InvalidExternalAppCredentialsException();

        String ruberToken = ruberTokensService.getNextToken();

        Integer vkId = req.getVk_user_id();
        String vkToken = req.getVk_access_token();

        if (usersService.isUserExist(vkId)) {
            usersService.addVkTokenToUser(vkId, vkToken);
            usersService.addRuberTokenToUser(vkId, ruberToken);
        } else {
            usersService.addUser(vkId, vkToken, ruberToken);
        }

        return new AuthResponse(ruberToken, vkId);
    }
}