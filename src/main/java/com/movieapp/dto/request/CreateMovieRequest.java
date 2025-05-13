package com.movieapp.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * Requête pour créer un nouveau film.
 */
public class CreateMovieRequest {

    @NotBlank(message = "Le titre est requis")
    private String title;

    private String synopsis;

    @Min(value = 1, message = "La durée doit être supérieure à 0")
    private int durationMinutes;

    @NotBlank(message = "La langue est requise")
    private String language;

    @NotBlank(message = "Le nom du réalisateur est requis")
    private String director;

    private Integer minAge;

    @NotNull(message = "La date de sortie est requise")
    private LocalDate releaseDate;

    // === Getters & Setters ===

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
