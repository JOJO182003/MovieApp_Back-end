package com.movieapp.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Requête d'authentification utilisateur via nom d'utilisateur et mot de passe.
 */
public class LoginRequest {

    @NotBlank(message = "Le nom d'utilisateur est requis")
    private String username;

    @NotBlank(message = "Le mot de passe est requis")
    private String password;

    // === Getters ===

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // === Setters ===

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
