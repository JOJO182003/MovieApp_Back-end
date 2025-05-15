package com.movieapp.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Movie {

    private final int id;
    private final String title;
    private final String synopsis;
    private final int durationMinutes;
    private final String language;
    private final String director;
    private final Integer minAge;
    private final LocalDate releaseDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private String thumbnail;

    public Movie(int id,
                 String title,
                 String synopsis,
                 int durationMinutes,
                 String language,
                 String director,
                 Integer minAge,
                 LocalDate releaseDate,
                 LocalDateTime createdAt,
                 LocalDateTime updatedAt, String thumbnail) {

        this.id = id;
        this.title = requireNonBlank(title, "Titre requis");
        this.synopsis = synopsis;
        this.durationMinutes = validateDuration(durationMinutes);
        this.language = language;
        this.director = director;
        this.minAge = minAge;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : LocalDateTime.now();
    }

    // === Getters ===

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getLanguage() {
        return language;
    }

    public String getDirector() {
        return director;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getThumbnail() { return this.thumbnail; }
    // === Méthodes utilitaires de validation ===

    private static String requireNonBlank(String value, String message) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(message);
        return value;
    }

    private static int validateDuration(int durationMinutes) {
        if (durationMinutes < 1) throw new IllegalArgumentException("Durée invalide");
        return durationMinutes;
    }

}
