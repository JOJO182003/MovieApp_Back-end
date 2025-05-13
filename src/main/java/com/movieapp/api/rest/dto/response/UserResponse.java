package com.movieapp.api.rest.dto.response;

public class UserResponse {

    private int id;
    private String username;
    private String email;
    private String role;

    public UserResponse(int id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public int getId() {
        return id;
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

