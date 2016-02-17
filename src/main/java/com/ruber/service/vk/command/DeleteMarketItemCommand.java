package com.ruber.service.vk.command;

import com.ruber.service.vk.VkException;
import com.ruber.service.vk.dto.DeleteMarketItemResponse;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class DeleteMarketItemCommand implements VkCommand<Integer> {
    private final Map<String, Object> params = new HashMap<>();

    public DeleteMarketItemCommand(Integer ownerId, Integer id, String vkAccessToken) {
        params.put("owner_id", ownerId);
        params.put("item_id", id);
        params.put("access_token", vkAccessToken);
    }

    @Override
    public Integer execute() throws VkException {
        DeleteMarketItemResponse response = new RestTemplate().getForObject(
            "https://api.vk.com/method/market.delete?" +
                "owner_id={owner_id}&item_id={item_id}&access_token={access_token}&v=5.45",
            DeleteMarketItemResponse.class,
            params
        );
        return response.getResponse();
    }
}
