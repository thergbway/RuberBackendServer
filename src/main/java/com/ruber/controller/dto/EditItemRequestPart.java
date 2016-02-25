package com.ruber.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditItemRequestPart {
    private Integer owner_id;
    private String name;
    private String description;
    private Integer category_id;
    private Double price;
    private Integer deleted;

    private Integer main_photo_crop_x;
    private Integer main_photo_crop_y;
    private Integer main_photo_crop_width;

    private Boolean has_photo_1;
    private Boolean has_photo_2;
    private Boolean has_photo_3;
    private Boolean has_photo_4;
}