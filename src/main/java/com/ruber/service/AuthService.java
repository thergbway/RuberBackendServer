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
        vkIdToVkAccessTokenMap.put(146812710, "19bd7b91a23daf445a57c2e8de16dc0a995db432eee284ae93dab8f3dcc6516b4f4e036616ddbc4734ece");
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