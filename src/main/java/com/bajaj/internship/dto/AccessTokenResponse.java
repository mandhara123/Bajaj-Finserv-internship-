package com.bajaj.internship.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResponse {
    @JsonProperty("accessToken")
    private String accessToken;
    
    public AccessTokenResponse() {}
    
    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    @Override
    public String toString() {
        return "AccessTokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}