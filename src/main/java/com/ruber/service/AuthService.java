package com.ruber.service;

import com.ruber.controller.dto.AuthRequest;
import com.ruber.controller.dto.AuthResponse;
import com.ruber.service.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private static final Map<Integer, String> vkIdToVkAccessTokenMap = new HashMap<>();
    private static final Map<String, Integer> ruberAccessTokenToVkIdMap = new HashMap<>();

    static {
        vkIdToVkAccessTokenMap.put(146812710, "af9958ef8ee1f92fd959af4bd9535723461796ba16bb2f07998dcb1085670be78bd99cf58495d3d09aa1d");
        ruberAccessTokenToVkIdMap.put("1455890049656_146812710", 146812710);
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
        Integer userId = req.getVk_user_id();

        vkIdToVkAccessTokenMap.put(userId, req.getVk_access_token());

        String ruberAccessToken = System.currentTimeMillis() + "_" + userId;
        ruberAccessTokenToVkIdMap.put(ruberAccessToken, userId);

        return new AuthResponse(ruberAccessToken, userId);
    }
}