package com.ruber.controller.dto;

import com.ruber.dao.entity.Order;
import com.ruber.dao.entity.OrderPosition;
import com.ruber.dao.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderRequest {
    private String title;
    private String description;
    private OrderStatus status;
    private Long deadline_timestamp;
    private Customer customer;
    private Shipment shipment;
    private Discount discount;
    private List<ItemReplica> item_replicas;
    private List<VkItemReplica> vk_item_replicas;

    public Order toOrder(Long createdTimestamp) {
        List<com.ruber.dao.entity.ItemReplica> itemReplicas =
            item_replicas.stream().map(ItemReplica::toEntity).collect(Collectors.toList());
        List<com.ruber.dao.entity.VkItemReplica> vkItemReplicas =
            vk_item_replicas.stream().map(VkItemReplica::toEntity).collect(Collectors.toList());

        LinkedList<OrderPosition> orderPositions = new LinkedList<>();
        orderPositions.addAll(itemReplicas);
        orderPositions.addAll(vkItemReplicas);

        return new Order(
            null,
            title,
            description,
            status,
            createdTimestamp,
            deadline_timestamp,
            discount != null ? discount.toEntity() : null,
            shipment != null ? shipment.toEntity() : null,
            orderPositions,
            customer.toEntity(),
            Collections.<com.ruber.dao.entity.PinnedItem>emptyList()
        );
    }

    @Data
    private static class Discount {
        private String title;
        private String description;
        private URL thumb_photo;
        private BigDecimal cost;

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

    @Data
    private static class Customer {
        private String name;
        private String phone;
        private Integer vk_id;

        public com.ruber.dao.entity.Customer toEntity() {
            return new com.ruber.dao.entity.Customer(
                null,
                name,
                phone,
                vk_id
            );
        }
    }

    @Data
    private static class Shipment {
        private String address;
        private BigDecimal cost;

        public com.ruber.dao.entity.Shipment toEntity() {
            return new com.ruber.dao.entity.Shipment(null, address, cost);
        }
    }

    @Data
    private static class ItemReplica {
        private String title;
        private String description;
        private URL thumb_photo;
        private BigDecimal price;
        private Integer amount;

        public com.ruber.dao.entity.ItemReplica toEntity() {
            return new com.ruber.dao.entity.ItemReplica(
                null,
                title,
                description,
                thumb_photo,
                price,
                amount
            );
        }
    }

    @Data
    private static class VkItemReplica {
        private String title;
        private String description;
        private URL thumb_photo;
        private BigDecimal price;
        private Integer amount;
        private Integer vk_id;
        private Integer vk_owner_id;

        public com.ruber.dao.entity.VkItemReplica toEntity() {
            return new com.ruber.dao.entity.VkItemReplica(
                null,
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
}