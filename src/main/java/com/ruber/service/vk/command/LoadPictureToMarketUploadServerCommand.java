package com.ruber.service.vk.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruber.service.vk.VkException;
import com.ruber.service.vk.dto.LoadPictureToMarketUploadServerResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class LoadPictureToMarketUploadServerCommand implements VkCommand<LoadPictureToMarketUploadServerResponse> {
    private final String uploadUrl;
    private final byte[] pictureBytes;
    private final String pictureExtention;


    public LoadPictureToMarketUploadServerCommand(String uploadUrl, byte[] pictureBytes, String pictureExtention) {
        this.uploadUrl = uploadUrl;
        this.pictureBytes = pictureBytes;
        this.pictureExtention = pictureExtention;
    }

    @Override
    public LoadPictureToMarketUploadServerResponse execute() throws VkException {
        try {
            Resource resource = new ByteArrayResource(pictureBytes) {
                @Override
                public String getFilename() {
                    return "pictureFile." + pictureExtention;
                }
            };

            MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add("file", resource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);

            ResponseEntity<String> responseEntity = new RestTemplate().exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);

            LoadPictureToMarketUploadServerResponse response = new ObjectMapper().readValue(
                responseEntity.getBody(), LoadPictureToMarketUploadServerResponse.class);

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}