package com.movieapp.api.rest.dto.response;

public class JwtResponse {

    private String token;
    private String username;

    public JwtResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    // Getters
}
