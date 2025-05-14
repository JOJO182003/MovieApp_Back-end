package com.movieapp.domain.model;

public class Theatre {

    private final int id;
    private final String name;
    private final String city;
    private final String address;

    public Theatre(int id, String name, String city, String address) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nom requis");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("Ville requise");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Adresse requise");
        }

        this.id = id;
        this.name = name;
        this.city = city;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    /**
     * Crée une nouvelle instance avec un nom mis à jour.
     */
    public Theatre withName(String newName) {
        return new Theatre(id, newName, city, address);
    }

    /**
     * Crée une nouvelle instance avec une adresse mise à jour.
     */
    public Theatre withAddress(String newAddress) {
        return new Theatre(id, name, city, newAddress);
    }
}
