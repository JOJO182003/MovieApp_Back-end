package com.movieapp.domain.model;

public class Role {

    private final int id;
    private final String name;

    public Role(int id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom du rôle est requis");
        }
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Crée une nouvelle instance avec le même id mais un nom modifié.
     */
    public Role withName(String newName) {
        return new Role(this.id, newName);
    }
}
