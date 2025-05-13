package com.movieapp.api.rest.dto.response;

import java.time.LocalDate;

public class MovieResponse {

    private int id;
    private String title;
    private String language;
    private String director;
    private int durationMinutes;
    private LocalDate releaseDate;

    public MovieResponse(int id, String title, String language, String director, int durationMinutes, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.director = director;
        this.durationMinutes = durationMinutes;
        this.releaseDate = releaseDate;
    }

    // Getters
}
