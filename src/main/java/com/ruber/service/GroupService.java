package com.ruber.service;

import com.ruber.controller.dto.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private AuthService authService;

    public Group[] getGroupsWithMarketItemsCount(String accessToken, Integer count, Integer offset) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        throw new RuntimeException();
    }
}
