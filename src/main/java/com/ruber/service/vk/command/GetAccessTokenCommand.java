package com.ruber.service.vk.command;

import com.ruber.service.vk.VkException;
import com.ruber.service.vk.dto.GetAccessTokenRequest;
import com.ruber.service.vk.dto.GetAccessTokenResponse;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class GetAccessTokenCommand implements VkCommand<GetAccessTokenResponse> {
    private final Map<String, Object> params = new HashMap<>();

    public GetAccessTokenCommand(GetAccessTokenRequest request) {
        params.put("client_id", request.getClient_id());
        params.put("client_secret", request.getClient_secret());
        params.put("redirect_uri", request.getRedirect_uri());
        params.put("code", request.getCode());
    }

    @Override
    public GetAccessTokenResponse execute() throws VkException {
        try {
            return new RestTemplate().getForObject(
                "https://oauth.vk.com/access_token?" +
                    "client_id={client_id}&client_secret={client_secret}&redirect_uri={redirect_uri}&code={code}",
                GetAccessTokenResponse.class, params
            );
        } catch (Exception e) {
            throw new VkException(e);
        }
    }
}