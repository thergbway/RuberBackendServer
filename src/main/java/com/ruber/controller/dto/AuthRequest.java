package com.ruber.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String code;
    private String redirectURI;
    private Integer ruberAppId;
    private String ruberAppSecret;
}
