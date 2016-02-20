package com.ruber.service.vk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveMarketPhotoResponse {
    private Integer id;
    private Integer album_id;
    private Integer owner_id;
    private Integer user_id;
    private String text;
    private Integer date;

    private String photo_75;
    private String photo_130;
    private String photo_604;
    private String photo_807;
    private String photo_1280;

    private Integer width;
    private Integer height;

}
