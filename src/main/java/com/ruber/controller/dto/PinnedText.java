package com.ruber.controller.dto;

import lombok.Data;

@Data
public class PinnedText {
    private Integer id;
    private Integer position;
    private String text;
    private Long created_timestamp;

    private PinnedText() {
    }

    public static PinnedText buildFromEntity(com.ruber.dao.entity.PinnedText entity) {
        PinnedText pinnedText = new PinnedText();

        pinnedText.setId(entity.getId());
        pinnedText.setText(entity.getText());
        pinnedText.setPosition(entity.getPosition());
        pinnedText.setCreated_timestamp(entity.getCreatedTimestamp());

        return pinnedText;
    }

    public com.ruber.dao.entity.PinnedText toEntity(Long createdTimestamp) {
        return new com.ruber.dao.entity.PinnedText(
            null,//fixme can be set to some value from controller
            position,
            createdTimestamp,
            text
        );
    }
}
