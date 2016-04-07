package com.ruber.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruber.dao.entity.VkItemReplica;
import com.ruber.exception.InvalidRequestJsonException;
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

    @JsonInclude(NON_NULL)
    private Integer vk_owner_id;

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
            itemReplica.setVk_owner_id(vk_entity.getVkOwnerId());
        }

        return itemReplica;
    }

    public com.ruber.dao.entity.ItemReplica toEntity() {
        if (vk_id == null && vk_owner_id == null)
            return new com.ruber.dao.entity.ItemReplica(
                null,//fixme can be set to some value from controller
                title,
                description,
                thumb_photo,
                price,
                amount
            );
        else if (vk_id != null && vk_owner_id != null)
            return new com.ruber.dao.entity.VkItemReplica(
                null,//fixme can be set to some value from controller
                title,
                description,
                thumb_photo,
                price,
                amount,
                vk_id,
                vk_owner_id
            );
        else
            throw new InvalidRequestJsonException("vk_id and vk_owner_id should be both specified or not");
    }
}