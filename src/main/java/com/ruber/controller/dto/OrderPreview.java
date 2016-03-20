package com.ruber.controller.dto;

import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderPreview {
    private Integer id;
    private String title;
    private String description;
    private OrderStatus status;
    private Integer order_positions;

    private OrderPreview() {
    }

    public static OrderPreview buildFromEntity(Order entity) {
        OrderPreview orderPreview = new OrderPreview();

        orderPreview.setId(entity.getId());
        orderPreview.setTitle(entity.getTitle());
        orderPreview.setDescription(entity.getDescription());
        orderPreview.setStatus(entity.getStatus());

        orderPreview.setOrder_positions(entity.getOrderPositions().size());

        return orderPreview;
    }
}