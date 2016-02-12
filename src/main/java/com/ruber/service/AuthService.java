package com.ruber.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.ruber.model.AuthRequest;
import com.ruber.model.AuthResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthService {
    private static final Map<Integer, String> vkIdToVkAccessTokenMap = new HashMap<>();
    private static final Map<String, Integer> ruberAccessTokenToVkIdMap = new HashMap<>();
    private static final Random rand = new Random();

    static {
        vkIdToVkAccessTokenMap.put(146812710, "2fc42a7c6836f1a68eb54a64dc9ff42b108d405f04d1d4777842acf1a0447d421154cdcf7441f12d2deee");
        ruberAccessTokenToVkIdMap.put("1455264594172_146812710", 146812710);
    }

    public Boolean checkAccessToken(String ruberAccessToken) {
        return ruberAccessTokenToVkIdMap.containsKey(ruberAccessToken);
    }

    public String getVkAccessToken(String ruberAccessToken) {
        Integer vkId = ruberAccessTokenToVkIdMap.get(ruberAccessToken);

        return vkIdToVkAccessTokenMap.get(vkId);
    }

    public AuthResponse authenticate(AuthRequest authRequest) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://oauth.vk.com/access_token")
            .queryString("client_id", 5242612)
            .queryString("client_secret", "REmRGbnkrKAqgEz2iac9")
            .queryString("redirect_uri", authRequest.getRedirectURI())
            .queryString("code", authRequest.getCode())
            .asJson();

        String vkAccessToken = response.getBody().getObject().getString("access_token");
        Integer userId = response.getBody().getObject().getInt("user_id");

        vkIdToVkAccessTokenMap.put(userId, vkAccessToken);
        String ruberAccessToken = System.currentTimeMillis() + "_" + userId;
        ruberAccessTokenToVkIdMap.put(ruberAccessToken, userId);

        return new AuthResponse(ruberAccessToken, userId);
    }
}