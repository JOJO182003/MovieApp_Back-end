package com.movieapp.api.rest.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Requête pour créer un nouveau théâtre dans l'application.
 */
public class CreateTheatreRequest {

    @NotBlank(message = "Le nom du théâtre est requis")
    private String name;

    @NotBlank(message = "La ville est requise")
    private String city;

    @NotBlank(message = "L'adresse est requise")
    private String address;

    // === Getters ===
    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    // === Setters ===
    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
