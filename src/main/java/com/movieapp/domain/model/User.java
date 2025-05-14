package com.movieapp.domain.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User {

    private final int id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final Role role;
    private final List<Theatre> theatres;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public User(int id, String username, String email, String passwordHash,
                Role role, List<Theatre> theatres,
                LocalDateTime createdAt, LocalDateTime updatedAt) {

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Nom d'utilisateur requis");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email requis");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Mot de passe requis");
        }
        if (role == null) {
            throw new IllegalArgumentException("Rôle requis");
        }

        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.theatres = theatres == null ? List.of() : List.copyOf(theatres);
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public List<Theatre> getTheatres() {
        return Collections.unmodifiableList(theatres);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Retourne un nouvel utilisateur avec un mot de passe mis à jour.
     */
    public User withHashedPassword(String hash, LocalDateTime updatedAt) {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("Mot de passe requis");
        }
        return new User(id, username, email, hash, role, theatres, createdAt, updatedAt);
    }

    /**
     * Retourne un nouvel utilisateur avec une nouvelle adresse email.
     */
    public User withEmail(String newEmail, LocalDateTime updatedAt) {
        if (newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("Email requis");
        }
        return new User(id, username, newEmail, passwordHash, role, theatres, createdAt, updatedAt);
    }

    /**
     * Retourne un nouvel utilisateur avec un rôle mis à jour.
     */
    public User withRole(Role newRole, LocalDateTime updatedAt) {
        if (newRole == null) {
            throw new IllegalArgumentException("Rôle requis");
        }
        return new User(id, username, email, passwordHash, newRole, theatres, createdAt, updatedAt);
    }
}
