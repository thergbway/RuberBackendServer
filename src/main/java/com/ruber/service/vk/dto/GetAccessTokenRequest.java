package com.ruber.service.vk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessTokenRequest {
    private Integer client_id;
    private String client_secret;
    private String redirect_uri;
    private String code;
}
