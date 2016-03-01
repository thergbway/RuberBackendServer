package com.ruber.service.vk.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruber.service.vk.VkException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class EditMarketItemCommand implements VkCommand<Void> {
    private final Map<String, Object> params = new HashMap<>();

    public EditMarketItemCommand(String vkAccessToken, Integer itemId, Integer ownerId, String name, String description,
                                 Integer category_id, Double price, Integer deleted,
                                 Integer mainPhotoId, Integer[] photoIds) {
        params.put("access_token", vkAccessToken);
        params.put("item_id", itemId);
        params.put("owner_id", ownerId);
        params.put("name", name);
        params.put("description", description);
        params.put("category_id", category_id);
        params.put("price", price);
        params.put("deleted", deleted);
        params.put("main_photo_id", mainPhotoId);

        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < photoIds.length; i++) {
            sb.append(photoIds[i]);
            if (i != photoIds.length - 1)
                sb.append(",");
        }

        String photoIdsStr = null;
        if (sb.toString().length() > 0)
            photoIdsStr = sb.toString();

        params.put("photo_ids", photoIdsStr);
    }

    @Override
    public Void execute() throws VkException {
        try {
            StringBuilder sb = new StringBuilder("");

            sb.append("https://api.vk.com/method/market.edit?" +
                "owner_id={owner_id}&item_id={item_id}&name={name}&description={description}&" +
                "category_id={category_id}&" +
                "price={price}&main_photo_id={main_photo_id}");
            if (params.get("deleted") != null)
                sb.append("&deleted={deleted}");
            if (params.get("photo_ids") != null)
                sb.append("&photo_ids={photo_ids}");
            sb.append("&access_token={access_token}&v=5.45");

            String response = new RestTemplate().getForObject(sb.toString(), String.class, params);

            int result = new ObjectMapper().readTree(response).get("response").asInt();

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
