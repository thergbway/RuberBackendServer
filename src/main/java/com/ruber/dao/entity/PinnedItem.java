package com.ruber.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "pinned_items")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "PinnedItem.deleteById", query = "delete from PinnedItem i where i.id = :pinnedItemId")
public abstract class PinnedItem {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private Integer position;

    @Column(name = "created_timestamp", nullable = false)
    private Long createdTimestamp;
}