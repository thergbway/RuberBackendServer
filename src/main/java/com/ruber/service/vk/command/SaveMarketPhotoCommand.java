package com.ruber.service.vk.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruber.service.vk.VkException;
import com.ruber.service.vk.dto.SaveMarketPhotoResponse;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class SaveMarketPhotoCommand implements VkCommand<SaveMarketPhotoResponse> {
    private final Map<String, Object> params = new HashMap<>();

    public SaveMarketPhotoCommand(String photo, Integer server, String hash, Integer groupId,
                                  String cropData, String cropHash, String vkAccessToken) {
        params.put("photo", photo);
        params.put("server", server);
        params.put("hash", hash);
        params.put("group_id", groupId);
        params.put("crop_data", cropData);
        params.put("crop_hash", cropHash);
        params.put("access_token", vkAccessToken);
    }

    @Override
    public SaveMarketPhotoResponse execute() throws VkException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("https://api.vk.com/method/photos.saveMarketPhoto?");
            sb.append("photo={photo}&server={server}&hash={hash}");

            if (params.get("group_id") != null)
                sb.append("&group_id={group_id}");

            if (params.get("crop_data") != null)
                sb.append("&crop_data={crop_data}");

            if (params.get("crop_hash") != null)
                sb.append("&crop_hash={crop_hash}");

            sb.append("&access_token={access_token}");
            sb.append("&v=5.45");

            String url = sb.toString();

            String response = new RestTemplate().getForObject(url, String.class, params);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJsonNode = mapper.readTree(response).get("response").get(0);
            SaveMarketPhotoResponse saveMarketPhotoResponse = mapper.treeToValue(responseJsonNode, SaveMarketPhotoResponse.class);

            return saveMarketPhotoResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}