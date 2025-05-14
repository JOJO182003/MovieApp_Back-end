package com.movieapp.api.rest.dto.response;

public class TheatreResponse {

    private int id;
    private String name;
    private String city;
    private String address;

    public TheatreResponse(int id, String name, String city, String address) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
    }

    public String getName() { return name; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
}
