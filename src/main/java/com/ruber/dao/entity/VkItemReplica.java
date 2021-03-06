package com.ruber.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.net.URL;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

@Entity
@Table(name = "vk_item_replicas")
@PrimaryKeyJoinColumn(name = "order_position_id")
public class VkItemReplica extends ItemReplica {
    @Column(name = "vk_id", nullable = false)
    private Integer vkId;

    public VkItemReplica(Integer id, String title, String description, URL thumbPhoto, BigDecimal price, Integer amount, Integer vkId) {
        super(id, title, description, thumbPhoto, price, amount);
        this.vkId = vkId;
    }
}