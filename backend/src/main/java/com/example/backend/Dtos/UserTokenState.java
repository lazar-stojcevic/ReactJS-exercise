package com.example.backend.Dtos;

public class UserTokenState {

    private String accessToken;
    private Long expiresIn;
    private Long id;
    private String role;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.id = null;
        this.role = null;
    }

    public UserTokenState(String accessToken, long expiresIn, long id, String role) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.role = role;
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public void setId(long id) {this.id = id; }

    public Long getId() {return this.id; }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
