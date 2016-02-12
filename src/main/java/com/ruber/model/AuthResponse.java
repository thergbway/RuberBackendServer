package com.ruber.model;

public class AuthResponse {
    private String ruberAccessToken;
    private Integer vkId;

    public String getRuberAccessToken() {
        return ruberAccessToken;
    }

    public void setRuberAccessToken(String ruberAccessToken) {
        this.ruberAccessToken = ruberAccessToken;
    }

    public Integer getVkId() {
        return vkId;
    }

    public void setVkId(Integer vkId) {
        this.vkId = vkId;
    }

    public AuthResponse(String ruberAccessToken, Integer vkId) {

        this.ruberAccessToken = ruberAccessToken;
        this.vkId = vkId;
    }

    public AuthResponse() {

    }
}
