package com.ruber.controller.dto;

import lombok.Data;

@Data
public class PinnedFile {
    private Integer id;
    private Integer position;
    private String file_name;

    private PinnedFile() {
    }

    public static PinnedFile buildFromEntity(com.ruber.dao.entity.PinnedFile entity) {
        PinnedFile pinnedFile = new PinnedFile();

        pinnedFile.setId(entity.getId());
        pinnedFile.setPosition(entity.getPosition());
        pinnedFile.setFile_name(entity.getFileName());

        return pinnedFile;
    }
}