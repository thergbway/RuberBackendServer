package com.ruber.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditGroupRequest {
    private Integer market;
    private Integer messages;
    private Integer market_contact;
}