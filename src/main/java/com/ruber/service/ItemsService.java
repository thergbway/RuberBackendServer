package com.ruber.service;

import com.ruber.controller.dto.GetItemResponse;
import com.ruber.controller.dto.GetItemsResponse;
import com.ruber.controller.dto.Item;
import com.ruber.service.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsService {
    @Autowired
    private AuthService authService;

    @Autowired
    private VkService vkService;

    public GetItemsResponse getItems(Integer ownerId, Integer count, Integer offset, String accessToken) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        if (count == null)
            count = 100;
        if (offset == null)
            offset = 0;

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        return vkService.getMarketItems(ownerId, count, offset, vkAccessToken);
    }

    public GetItemResponse getItem(Integer ownerId, Integer id, String accessToken) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        Item item = vkService.getMarketItem(ownerId, id, vkAccessToken);

        return new GetItemResponse(item);
    }

    public void deleteMarketItem(Integer ownerId, Integer id, String accessToken) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        vkService.deleteMarketItem(ownerId, id, vkAccessToken);
    }
}