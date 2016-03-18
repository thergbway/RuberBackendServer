package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.net.URL;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "thumb_photo", nullable = false)
    private URL thumbPhoto;

    @Column(nullable = false)
    private BigDecimal cost;
}
