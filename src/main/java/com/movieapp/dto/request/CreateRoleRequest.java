package com.movieapp.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Requête pour créer un rôle utilisateur dans le système.
 */
public class CreateRoleRequest {

    @NotBlank(message = "Le nom du rôle est requis")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
