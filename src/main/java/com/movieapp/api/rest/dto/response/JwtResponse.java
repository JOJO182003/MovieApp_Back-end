package com.movieapp.api.rest.dto.response;

public class JwtResponse {

    private final String token;
    private final String username;

    public JwtResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }
}
