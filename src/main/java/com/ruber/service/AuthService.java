package com.ruber.service;

import com.ruber.controller.dto.AuthRequest;
import com.ruber.controller.dto.AuthResponse;
import com.ruber.service.vk.VkService;
import com.ruber.service.vk.dto.GetAccessTokenRequest;
import com.ruber.service.vk.dto.GetAccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private static final Map<Integer, String> vkIdToVkAccessTokenMap = new HashMap<>();
    private static final Map<String, Integer> ruberAccessTokenToVkIdMap = new HashMap<>();

    static {
        vkIdToVkAccessTokenMap.put(146812710, "294083358f00ace6940b42453c4ce2473b23f4e182e6627647b72e0122a0685bb95f4c5465666f3130e14");
        ruberAccessTokenToVkIdMap.put("1455430739207_146812710", 146812710);
    }

    @Autowired
    private VkService vkService;

    public Boolean checkAccessToken(String ruberAccessToken) {
        return ruberAccessTokenToVkIdMap.containsKey(ruberAccessToken);
    }

    public String getVkAccessToken(String ruberAccessToken) {
        Integer vkId = ruberAccessTokenToVkIdMap.get(ruberAccessToken);

        return vkIdToVkAccessTokenMap.get(vkId);
    }

    public AuthResponse authenticate(AuthRequest req) {
        GetAccessTokenRequest getAccessTokenRequest =
            new GetAccessTokenRequest(5242612, "REmRGbnkrKAqgEz2iac9", req.getRedirectURI(), req.getCode());
        GetAccessTokenResponse resp = vkService.getAccessToken(getAccessTokenRequest);

        Integer userId = resp.getUserId();

        vkIdToVkAccessTokenMap.put(userId, resp.getAccessToken());
        String ruberAccessToken = System.currentTimeMillis() + "_" + userId;
        ruberAccessTokenToVkIdMap.put(ruberAccessToken, userId);

        return new AuthResponse(ruberAccessToken, userId);
    }
}