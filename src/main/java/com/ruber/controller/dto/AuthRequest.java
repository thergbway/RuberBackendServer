package com.ruber.controller.dto;

public class AuthRequest {
    private String code;
    private String redirectURI;
    private Integer ruberAppId;
    private String ruberAppSecret;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectURI() {
        return redirectURI;
    }

    public void setRedirectURI(String redirectURI) {
        this.redirectURI = redirectURI;
    }

    public Integer getRuberAppId() {
        return ruberAppId;
    }

    public void setRuberAppId(Integer ruberAppId) {
        this.ruberAppId = ruberAppId;
    }

    public String getRuberAppSecret() {
        return ruberAppSecret;
    }

    public void setRuberAppSecret(String ruberAppSecret) {
        this.ruberAppSecret = ruberAppSecret;
    }

    public AuthRequest(String code, String redirectURI, Integer ruberAppId, String ruberAppSecret) {
        this.code = code;
        this.redirectURI = redirectURI;
        this.ruberAppId = ruberAppId;
        this.ruberAppSecret = ruberAppSecret;
    }

    public AuthRequest() {

    }
}
