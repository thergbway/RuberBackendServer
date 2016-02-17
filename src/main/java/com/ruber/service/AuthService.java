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
        vkIdToVkAccessTokenMap.put(146812710, "64b127623dc73c8739568f753727ff7f28333b9f7bc094c9b10ad62854b9ae3faa0538a118c5b1f22d0b3");
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
            new GetAccessTokenRequest(5242612, "REmRGbnkrKAqgEz2iac9", req.getRedirect_uri(), req.getCode());
        GetAccessTokenResponse resp = vkService.getAccessToken(getAccessTokenRequest);

        Integer userId = resp.getUser_id();

        vkIdToVkAccessTokenMap.put(userId, resp.getAccess_token());
        String ruberAccessToken = System.currentTimeMillis() + "_" + userId;
        ruberAccessTokenToVkIdMap.put(ruberAccessToken, userId);

        return new AuthResponse(ruberAccessToken, userId);
    }
}