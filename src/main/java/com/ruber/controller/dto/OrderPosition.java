package com.ruber.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPosition {
    private Integer id;
    private Integer vk_id;
    private Integer vk_owner_id;
    private String title;
    private String description;
    private Integer price;
    private Integer amount;
    private String thumb_photo;

    public static OrderPosition buildFromOrderPosition(com.ruber.dao.entity.OrderPosition position) {
        return new OrderPosition(
            position.getId(),
            position.getVkId(),
            position.getVkOwnerId(),
            position.getTitle(),
            position.getDescription(),
            position.getPrice(),
            position.getAmount(),
            position.getThumbPhoto()
        );
    }
}