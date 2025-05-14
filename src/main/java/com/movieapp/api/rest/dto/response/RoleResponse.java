package com.movieapp.api.rest.dto.response;

public class RoleResponse {

    private int id;
    private String name;

    public RoleResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
