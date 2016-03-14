package com.ruber.dao.entity;

import com.ruber.controller.dto.AddOrderRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@NoArgsConstructor

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "owner_id", nullable = false)
    private Integer ownerId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_timestamp", nullable = false)
    private Long createdTimestamp;

    @Column(name = "deadline_timestamp")
    private Long deadlineTimestamp;

    @Column(name = "shipment_cost", nullable = false)
    private Integer shipmentCost;

    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @OneToMany(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "order_id")
    private List<OrderPosition> orderPositions;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "pinned_messages", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "message_id")
    private List<Integer> pinnedMessageIds;

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "pinned_texts", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "text")
    private List<String> pinnedTexts;

    public Order(String title, Integer ownerId, Integer customerId, Long createdTimestamp, Long deadlineTimestamp,
                 Integer shipmentCost, OrderStatus status, List<OrderPosition> orderPositions,
                 List<Integer> pinnedMessageIds, List<String> pinnedTexts) {
        this.title = title;
        this.ownerId = ownerId;
        this.customerId = customerId;
        this.createdTimestamp = createdTimestamp;
        this.deadlineTimestamp = deadlineTimestamp;
        this.shipmentCost = shipmentCost;
        this.status = status;
        this.orderPositions = orderPositions;
        this.pinnedMessageIds = pinnedMessageIds;
        this.pinnedTexts = pinnedTexts;
    }

    public static Order buildFromAddOrderRequest(AddOrderRequest orderRequest, Integer ownerId) {
        List<OrderPosition> orderPositions = orderRequest
            .getOrder_positions()
            .stream()
            .map(OrderPosition::buildFromAddOrderRequestPosition)
            .collect(Collectors.toList());

        return new Order(
            orderRequest.getTitle(),
            ownerId,
            orderRequest.getCustomer_id(),
            orderRequest.getCreated_timestamp(),
            orderRequest.getDeadline_timestamp(),
            orderRequest.getShipment_cost(),
            orderRequest.getStatus(),
            orderPositions,
            Collections.<Integer>emptyList(),
            Collections.<String>emptyList()

        );
    }

}