package com.movieapp.api.rest.dto.response;

public class UserInfoResponse {

    private final int id;
    private final String username;
    private final String role;

    public UserInfoResponse(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
