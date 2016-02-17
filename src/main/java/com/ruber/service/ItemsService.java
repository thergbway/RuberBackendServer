package com.ruber.service;

import com.ruber.controller.dto.Item;
import com.ruber.controller.dto.ItemResponse;
import com.ruber.controller.dto.ItemsResponse;
import com.ruber.service.vk.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsService {
    @Autowired
    private AuthService authService;

    @Autowired
    private VkService vkService;

    public ItemsResponse getItems(Integer ownerId, Integer count, Integer offset, String accessToken) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        if (count == null)
            count = 100;
        if (offset == null)
            offset = 0;

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        return vkService.getMarketItems(ownerId, count, offset, vkAccessToken);
    }

    public ItemResponse getItem(Integer ownerId, Integer id, String accessToken) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        Item item = vkService.getMarketItem(ownerId, id, vkAccessToken);
        ItemResponse itemResponse = new ItemResponse(item);

        return itemResponse;
    }

    public void deleteMarketItem(Integer ownerId, Integer id, String accessToken) {
        if (!authService.checkAccessToken(accessToken))
            throw new RuntimeException("Invalid accessToken");

        String vkAccessToken = authService.getVkAccessToken(accessToken);

        vkService.deleteMarketItem(ownerId, id, vkAccessToken);
    }
}