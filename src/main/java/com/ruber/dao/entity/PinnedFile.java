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
@Table(name = "pinned_files")
@PrimaryKeyJoinColumn(name = "pinned_item_id")
public class PinnedFile extends PinnedItem {
    @Column(nullable = false)
    private byte[] content;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    public PinnedFile(Integer id, Integer position, byte[] content, String fileName) {
        super(id, position);
        this.content = content;
        this.fileName = fileName;
    }
}