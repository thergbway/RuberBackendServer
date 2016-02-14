package com.ruber.service.vk.dto;

public class GetAccessTokenRequest {
    private Integer clientId;
    private String clientSecret;
    private String redirectURI;
    private String code;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public GetAccessTokenRequest(Integer clientId, String clientSecret, String redirectURI, String code) {

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectURI = redirectURI;
        this.code = code;
    }

    public GetAccessTokenRequest() {

    }
}
