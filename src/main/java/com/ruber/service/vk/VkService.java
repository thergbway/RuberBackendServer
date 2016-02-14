package com.ruber.service.vk;

import com.ruber.service.vk.command.GetAccessTokenCommand;
import com.ruber.service.vk.dto.GetAccessTokenRequest;
import com.ruber.service.vk.dto.GetAccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
public class VkService {
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request) throws VkException {
        return new GetAccessTokenCommand(request).execute();
    }
}
