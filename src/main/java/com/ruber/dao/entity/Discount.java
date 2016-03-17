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
@Table(name = "discounts")
@PrimaryKeyJoinColumn(name = "id")
public class Discount extends OrderPosition {
    @Column(nullable = false)
    private BigDecimal cost;

    public Discount(Integer id, String title, String description, URL thumbPhoto, BigDecimal cost) {
        super(id, title, description, thumbPhoto);
        this.cost = cost;
    }
}
