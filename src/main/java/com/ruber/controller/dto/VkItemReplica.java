package com.ruber.controller.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.net.URL;

@Data
public class VkItemReplica {
    private Integer id;
    private String title;
    private String description;
    private URL thumb_photo;
    private BigDecimal price;
    private Integer amount;
    private Integer vk_id;
    private Integer vk_owner_id;

    private VkItemReplica() {
    }

    public static VkItemReplica buildFromEntity(com.ruber.dao.entity.VkItemReplica entity) {
        VkItemReplica vkItemReplica = new VkItemReplica();

        vkItemReplica.setId(entity.getId());
        vkItemReplica.setTitle(entity.getTitle());
        vkItemReplica.setDescription(entity.getDescription());
        vkItemReplica.setThumb_photo(entity.getThumbPhoto());
        vkItemReplica.setPrice(entity.getPrice());
        vkItemReplica.setAmount(entity.getAmount());
        vkItemReplica.setVk_id(entity.getVkId());
        vkItemReplica.setVk_owner_id(entity.getVkOwnerId());

        return vkItemReplica;
    }

    public com.ruber.dao.entity.VkItemReplica toEntity() {
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
    }
}
