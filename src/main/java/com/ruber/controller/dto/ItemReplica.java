package com.ruber.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.net.URL;

@Data
public class ItemReplica {
    private Integer id;
    private String title;
    private String description;
    private URL thumb_photo;
    private BigDecimal price;
    private Integer amount;

    private ItemReplica() {
    }

    public static ItemReplica buildFromEntity(com.ruber.dao.entity.ItemReplica entity) {
        ItemReplica itemReplica = new ItemReplica();

        itemReplica.setId(entity.getId());
        itemReplica.setTitle(entity.getTitle());
        itemReplica.setDescription(entity.getDescription());
        itemReplica.setThumb_photo(entity.getThumbPhoto());
        itemReplica.setPrice(entity.getPrice());
        itemReplica.setAmount(entity.getAmount());

        return itemReplica;
    }

    public com.ruber.dao.entity.ItemReplica toEntity() {
        return new com.ruber.dao.entity.ItemReplica(
            null,//fixme can be set to some value from controller
            title,
            description,
            thumb_photo,
            price,
            amount
        );
    }
}
