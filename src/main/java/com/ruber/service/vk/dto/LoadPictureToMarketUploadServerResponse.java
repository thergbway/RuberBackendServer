package com.ruber.service.vk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadPictureToMarketUploadServerResponse {
    private Integer server;
    private String photo;
    private String hash;
    private String crop_data;
    private String crop_hash;
}