package com.movieapp.dto.response;

public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";

    private int userId;
    private String username;
    private String email;
    private String role;

    public AuthResponse(String token, int userId, String username, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Getters

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
