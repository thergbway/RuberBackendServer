package com.ruber.service.vk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessTokenRequest {
    private Integer clientId;
    private String clientSecret;
    private String redirectURI;
    private String code;
}
