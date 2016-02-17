package com.ruber.service.vk.command;

import com.ruber.controller.dto.ItemsResponse;
import com.ruber.service.vk.VkException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class GetMarketItemsCommand implements VkCommand<ItemsResponse> {
    private final Map<String, Object> params = new HashMap<>();

    public GetMarketItemsCommand(Integer ownerId, Integer count, Integer offset, String vkAccessToken) {
        params.put("owner_id", ownerId);
        params.put("count", count);
        params.put("offset", offset);
        params.put("access_token", vkAccessToken);
    }

    @Override
    public ItemsResponse execute() throws VkException {
        try {
            return new RestTemplate().getForObject(
                "https://api.vk.com/method/market.get?" +
                    "owner_id={owner_id}&count={count}&offset={offset}&access_token={access_token}&v=5.45",
                com.ruber.service.vk.dto.GetMarketItemsResponse.class,
                params
            )
                .getResponse();
        } catch (Exception e) {
            throw new VkException(e);
        }
    }
}