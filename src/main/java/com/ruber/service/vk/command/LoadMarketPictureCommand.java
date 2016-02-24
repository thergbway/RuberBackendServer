package com.ruber.service.vk.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruber.service.vk.VkException;
import com.ruber.service.vk.dto.LoadMarketPictureResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class LoadMarketPictureCommand implements VkCommand<LoadMarketPictureResponse> {
    private final String uploadUrl;
    private final byte[] pictureBytes;
    private final String pictureFileName;


    public LoadMarketPictureCommand(String uploadUrl, byte[] pictureBytes, String pictureFileName) {
        this.uploadUrl = uploadUrl;
        this.pictureBytes = pictureBytes;
        this.pictureFileName = pictureFileName;
    }

    @Override
    public LoadMarketPictureResponse execute() throws VkException {
        try {
            Resource resource = new ByteArrayResource(pictureBytes) {
                @Override
                public String getFilename() {
                    return pictureFileName;
                }
            };

            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("file", resource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

            ResponseEntity<String> responseEntity = new RestTemplate().exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);

            LoadMarketPictureResponse response = new ObjectMapper().readValue(
                responseEntity.getBody(), LoadMarketPictureResponse.class);

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}