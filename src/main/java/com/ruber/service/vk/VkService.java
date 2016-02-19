package com.ruber.service.vk;

import com.ruber.controller.dto.GetItemsResponse;
import com.ruber.controller.dto.Item;
import com.ruber.service.vk.command.*;
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

    public GetItemsResponse getMarketItems(Integer ownerId, Integer count, Integer offset, String vkAccessToken) {
        return new GetMarketItemsCommand(ownerId, count, offset, vkAccessToken).execute();
    }

    public Item getMarketItem(Integer ownerId, Integer id, String vkAccessToken) {
        return new GetMarketItemCommand(ownerId, id, vkAccessToken).execute();
    }

    public void deleteMarketItem(Integer ownerId, Integer id, String vkAccessToken) {
        new DeleteMarketItemCommand(ownerId, id, vkAccessToken).execute();
    }
}