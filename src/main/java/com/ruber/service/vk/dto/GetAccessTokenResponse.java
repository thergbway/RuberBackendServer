package com.ruber.service.vk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccessTokenResponse {
    private String access_token;
    private Integer user_id;
}
