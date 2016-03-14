package com.ruber.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor

@Entity
@Table(name = "order_positions")
public class OrderPosition {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vk_id")
    private Integer vkId;

    @Column(name = "vk_owner_id")
    private Integer vkOwnerId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "thumb_photo", nullable = false)
    private String thumbPhoto;

    public OrderPosition(Integer vkId, Integer vkOwnerId, String title,
                         String description, Integer price, Integer amount, String thumbPhoto) {
        this.vkId = vkId;
        this.vkOwnerId = vkOwnerId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.thumbPhoto = thumbPhoto;
    }

    public static OrderPosition buildFromAddOrderRequestPosition(com.ruber.controller.dto.OrderPosition position) {
        return new OrderPosition(
            position.getVk_id(),
            position.getVk_owner_id(),
            position.getTitle(),
            position.getDescription(),
            position.getPrice(),
            position.getAmount(),
            position.getThumb_photo()
        );
    }
}