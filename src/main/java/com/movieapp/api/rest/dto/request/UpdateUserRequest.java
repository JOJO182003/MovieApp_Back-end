package com.movieapp.api.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Requête de mise à jour des informations d’un utilisateur.
 * Tous les champs sont requis sauf le mot de passe et le rôle.
 */
public class UpdateUserRequest {

    @NotBlank(message = "Le nom d'utilisateur est requis")
    private String username;

    @NotBlank(message = "L'adresse email est requise")
    @Email(message = "L'adresse email doit être valide")
    private String email;

    // Mot de passe facultatif : s'il est présent, il sera mis à jour
    private String password;

    // roleId facultatif : utilisé uniquement si changement de rôle autorisé
    private Integer roleId;

    // === Getters ===

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    // === Setters ===

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
