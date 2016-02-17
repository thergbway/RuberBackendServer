package com.ruber.service.vk;

import com.ruber.controller.dto.ItemsResponse;
import com.ruber.service.vk.command.GetAccessTokenCommand;
import com.ruber.service.vk.command.GetGroupsCommand;
import com.ruber.service.vk.command.GetMarketItemsCommand;
import com.ruber.service.vk.dto.GetAccessTokenRequest;
import com.ruber.service.vk.dto.GetAccessTokenResponse;
import com.ruber.service.vk.dto.GetGroupsResponse;
import org.springframework.stereotype.Service;

@Service
public class VkService {
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request) throws VkException {
        return new GetAccessTokenCommand(request).execute();
    }
    
    public GetGroupsResponse getGroups(Integer count, Integer offset, String vkAccessToken) throws VkException {
        return new GetGroupsCommand(count, offset, vkAccessToken).execute();
    }

    public ItemsResponse getMarketItems(Integer ownerId, Integer count, Integer offset, String vkAccessToken) {
        return new GetMarketItemsCommand(ownerId, count, offset, vkAccessToken).execute();
    }
}