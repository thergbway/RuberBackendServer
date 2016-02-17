package com.ruber.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String code;
    private String redirect_uri;
    private Integer ruber_app_id;
    private String ruber_app_secret;
}
