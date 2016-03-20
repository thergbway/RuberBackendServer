package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

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
    private List<PinnedMessage> pinnedMessages;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private List<PinnedText> pinnedTexts;

    @OneToMany(cascade = ALL)
    @JoinTable(
        name = "pinned_files",
        joinColumns = @JoinColumn(name = "order_id", nullable = false),
        inverseJoinColumns = @JoinColumn(name = "file_id", nullable = false)
    )
    private List<File> pinnedFiles;
}