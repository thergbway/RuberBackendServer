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
@Table(name = "pinned_messages")
@NamedQuery(name = "PinnedMessage.deleteById", query = "delete from PinnedMessage m where m.id = :pinnedMessageId")
public class PinnedMessage {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @Column(name = "vk_message_id", nullable = false)
    private Long vkMessageId;
}
