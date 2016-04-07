package com.ruber.controller.dto;

import com.ruber.dao.entity.OrderStatus;
import com.ruber.dao.entity.PinnedItem;
import lombok.Data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Order {
    private Integer id;
    private String title;
    private String description;
    private OrderStatus status;
    private Long created_timestamp;
    private Long deadline_timestamp;
    private Customer customer;
    private Shipment shipment;
    private Discount discount;
    private List<ItemReplica> item_replicas;

    private Order() {
    }

    public static Order buildFromOrder(com.ruber.dao.entity.Order order) {
        List<ItemReplica> itemReplicas = order
            .getOrderPositions()
            .stream()
            .filter(position -> position instanceof com.ruber.dao.entity.ItemReplica)
            .map(itemReplica -> ((com.ruber.dao.entity.ItemReplica) itemReplica))
            .map(ItemReplica::buildFromEntity)
            .collect(Collectors.toList());

        Order response = new Order();

        response.setId(order.getId());
        response.setTitle(order.getTitle());
        response.setDescription(order.getDescription());
        response.setStatus(order.getStatus());
        response.setCreated_timestamp(order.getCreatedTimestamp());
        response.setDeadline_timestamp(order.getDeadlineTimestamp());
        response.setCustomer(Customer.buildFromEntity(order.getCustomer()));

        if (order.getDiscount() != null)
            response.setDiscount(Discount.buildFromEntity(order.getDiscount()));

        if (order.getShipment() != null)
            response.setShipment(Shipment.buildFromEntity(order.getShipment()));

        response.setItem_replicas(itemReplicas);

        return response;
    }

    public com.ruber.dao.entity.Order toEntity(Long createdTimestamp) {
        List<com.ruber.dao.entity.ItemReplica> itemReplicas = item_replicas
            .stream()
            .map(ItemReplica::toEntity)
            .collect(Collectors.toList());

        return new com.ruber.dao.entity.Order(
            null,
            title,
            description,
            status,
            createdTimestamp,
            deadline_timestamp,
            discount != null ? discount.toEntity() : null,
            shipment != null ? shipment.toEntity() : null,
            new LinkedList<>(itemReplicas),
            customer.toEntity(),
            Collections.<PinnedItem>emptyList()
        );
    }
}