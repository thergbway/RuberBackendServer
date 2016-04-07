package com.ruber.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.net.URL;

@Data
public class Discount {
    private String title;
    private String description;
    private URL thumb_photo;
    private BigDecimal cost;

    private Discount() {
    }

    public static Discount buildFromEntity(com.ruber.dao.entity.Discount entity) {
        Discount discount = new Discount();

        discount.setTitle(entity.getTitle());
        discount.setDescription(entity.getDescription());
        discount.setThumb_photo(entity.getThumbPhoto());
        discount.setCost(entity.getCost().negate());

        return discount;
    }

    public com.ruber.dao.entity.Discount toEntity() {
        return new com.ruber.dao.entity.Discount(
            null,
            title,
            description,
            thumb_photo,
            cost.negate()
        );
    }
}