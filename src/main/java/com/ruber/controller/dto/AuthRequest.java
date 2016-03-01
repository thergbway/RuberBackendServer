package com.ruber.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String vk_access_token;
    private Integer vk_user_id;
    private Integer ruber_app_id;
    private String ruber_app_secret;
}
