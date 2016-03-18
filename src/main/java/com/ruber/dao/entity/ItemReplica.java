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
@Table(name = "item_replicas")
@PrimaryKeyJoinColumn(name = "order_position_id")
public class ItemReplica extends OrderPosition {
    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer amount;

    public ItemReplica(Integer id, String title, String description, URL thumbPhoto, BigDecimal price, Integer amount) {
        super(id, title, description, thumbPhoto);
        this.price = price;
        this.amount = amount;
    }
}