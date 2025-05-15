package com.movieapp.api.rest.dto.response;

import java.time.LocalDate;

public class MovieResponse {

    private int id;
    private String title;
    private String language;
    private String director;
    private int durationMinutes;
    private LocalDate releaseDate;
    private String thumbnail;

    public MovieResponse(int id, String title, String language, String director, int durationMinutes, LocalDate releaseDate, String thumbnail) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.director = director;
        this.durationMinutes = durationMinutes;
        this.releaseDate = releaseDate;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public String getDirector() {
        return director;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
}
