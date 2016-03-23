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
@Table(name = "pinned_messages")
@PrimaryKeyJoinColumn(name = "pinned_item_id")
public class PinnedMessage extends PinnedItem {
    @Column(name = "vk_message_id", nullable = false)
    private Long vkMessageId;

    public PinnedMessage(Integer id, Integer position, Long createdTimestamp, Long vkMessageId) {
        super(id, position, createdTimestamp);
        this.vkMessageId = vkMessageId;
    }
}
