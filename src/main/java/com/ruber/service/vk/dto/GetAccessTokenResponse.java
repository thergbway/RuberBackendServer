package com.ruber.service.vk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("user_id")
    private Integer userId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GetAccessTokenResponse(String accessToken, Integer userId) {

        this.accessToken = accessToken;
        this.userId = userId;
    }

    public GetAccessTokenResponse() {

    }
}
