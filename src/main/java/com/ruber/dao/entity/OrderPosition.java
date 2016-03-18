package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.net.URL;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "order_positions")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name = "OrderPosition.deleteById", query = "delete from OrderPosition p where p.id = :positionId")
})
public abstract class OrderPosition {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "thumb_photo", nullable = false)
    private URL thumbPhoto;
}