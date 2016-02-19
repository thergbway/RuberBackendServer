package com.ruber.service.vk.command;

import com.ruber.controller.dto.Item;
import com.ruber.service.vk.VkException;
import com.ruber.service.vk.dto.GetMarketItemResponse;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class GetMarketItemCommand implements VkCommand<Item> {
    private final Map<String, Object> params = new HashMap<>();

    public GetMarketItemCommand(Integer ownerId, Integer id, String vkAccessToken) {
        params.put("item_ids", ownerId + "_" + id);
        params.put("access_token", vkAccessToken);
    }

    @Override
    public Item execute() throws VkException {
        GetMarketItemResponse response = new RestTemplate().getForObject(
            "https://api.vk.com/method/market.getById?" +
                "item_ids={item_ids}&access_token={access_token}&v=5.45",
            GetMarketItemResponse.class,
            params
        );

        return response.getResponse().getItems()[0];
    }
}
