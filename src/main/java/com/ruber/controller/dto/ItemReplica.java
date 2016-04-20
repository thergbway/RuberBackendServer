package com.ruber.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruber.dao.entity.VkItemReplica;
import lombok.Data;

import java.math.BigDecimal;
import java.net.URL;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
public class ItemReplica {
    private Integer id;
    private String title;
    private String description;
    private URL thumb_photo;
    private BigDecimal price;
    private Integer amount;

    @JsonInclude(NON_NULL)
    private Integer vk_id;

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

        if (entity instanceof com.ruber.dao.entity.VkItemReplica) {
            VkItemReplica vk_entity = (VkItemReplica) entity;

            itemReplica.setVk_id(vk_entity.getVkId());
        }

        return itemReplica;
    }

    public com.ruber.dao.entity.ItemReplica toEntity() {
        if (vk_id == null)
            return new com.ruber.dao.entity.ItemReplica(
                null,
                title,
                description,
                thumb_photo,
                price,
                amount
            );
        else return new com.ruber.dao.entity.VkItemReplica(
            null,
                title,
                description,
                thumb_photo,
                price,
                amount,
            vk_id
            );
    }
}