package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "created_timestamp", nullable = false)
    private Long createdTimestamp;

    @Column(name = "deadline_timestamp")
    private Long deadlineTimestamp;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "shipment_id")
    private Shipment shipment;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private List<OrderPosition> orderPositions;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private List<PinnedItem> pinnedItems;
}