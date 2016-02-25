package com.ruber.service.vk.command;

import com.ruber.service.vk.VkException;
import com.ruber.service.vk.dto.GetMarketUploadServerResponse;
import lombok.Data;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class GetMarketUploadServerCommand implements VkCommand<GetMarketUploadServerResponse> {
    private final Map<String, Object> params = new HashMap<>();

    public GetMarketUploadServerCommand(Integer groupId, Integer mainPhoto,
                                        Integer cropX, Integer cropY, Integer cropWidth, String vkAccessToken) {
        params.put("group_id", groupId);
        params.put("main_photo", mainPhoto);
        params.put("crop_x", cropX);
        params.put("crop_y", cropY);
        params.put("crop_width", cropWidth);
        params.put("access_token", vkAccessToken);
    }

    @Override
    public GetMarketUploadServerResponse execute() throws VkException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("https://api.vk.com/method/photos.getMarketUploadServer?" +
                "group_id={group_id}&main_photo={main_photo}");

            if (params.get("crop_x") != null || params.get("crop_y") != null || params.get("crop_width") != null)
                if (params.get("crop_x") != null && params.get("crop_y") != null && params.get("crop_width") != null)
                    if (params.get("main_photo") != null)
                        sb.append("&crop_x={crop_x}&crop_y={crop_y}&crop_width={crop_width}");
                    else
                        throw new IllegalArgumentException("Crop parameters can be set when mainPhoto parameter equals 1");
                else
                    throw new IllegalArgumentException("Some of the parameters equal null");

            sb.append("&access_token={access_token}&v=5.45");

            GetMarketUploadServerWrapperResponse response = new RestTemplate().getForObject(sb.toString(),
                GetMarketUploadServerWrapperResponse.class, params);

            return response.getResponse();
        } catch (Exception e) {
            throw new VkException(e);
        }
    }

    @Data
    private static class GetMarketUploadServerWrapperResponse {
        private GetMarketUploadServerResponse response;
    }
}