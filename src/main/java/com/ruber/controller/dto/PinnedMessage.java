package com.ruber.controller.dto;

import lombok.Data;

@Data
public class PinnedMessage {
    private Integer id;
    private Integer position;
    private Long vk_message_id;

    private PinnedMessage() {
    }

    public static PinnedMessage buildFromEntity(com.ruber.dao.entity.PinnedMessage entity) {
        PinnedMessage pinnedMessage = new PinnedMessage();

        pinnedMessage.setId(entity.getId());
        pinnedMessage.setVk_message_id(entity.getVkMessageId());
        pinnedMessage.setPosition(entity.getPosition());

        return pinnedMessage;
    }

    public com.ruber.dao.entity.PinnedMessage toEntity() {
        return new com.ruber.dao.entity.PinnedMessage(
            null,//fixme can be set to some value from controller
            position,
            vk_message_id
        );
    }
}