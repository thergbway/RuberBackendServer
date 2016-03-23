package com.ruber.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

@Entity
@Table(name = "pinned_texts")
@PrimaryKeyJoinColumn(name = "pinned_item_id")
public class PinnedText extends PinnedItem {
    @Column(nullable = false)
    private String text;

    public PinnedText(Integer id, Integer position, Long createdTimestamp, String text) {
        super(id, position, createdTimestamp);
        this.text = text;
    }
}
