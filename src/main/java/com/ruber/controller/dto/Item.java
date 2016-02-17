package com.ruber.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer id;
    private Integer owner_id;
    private String title;
    private String description;
    private Price price;
    private Category category;
    private String thumb_photo;
    private Integer date;
    private Integer availability;
}
