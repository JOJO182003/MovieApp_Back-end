package com.movieapp.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MovieSchedule {

    private final int id;
    private final Movie movie;
    private final Theatre theatre;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String daysOfWeek;
    private final LocalTime time;
    private final LocalDateTime createdAt;

    public MovieSchedule(int id, Movie movie, Theatre theatre,
                         LocalDate startDate, LocalDate endDate,
                         String daysOfWeek, LocalTime time, LocalDateTime createdAt) {

        if (movie == null || theatre == null) {
            throw new IllegalArgumentException("Film et théâtre requis");
        }
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Dates invalides");
        }
        if (daysOfWeek == null || daysOfWeek.isBlank()) {
            throw new IllegalArgumentException("Jours requis");
        }
        if (time == null) {
            throw new IllegalArgumentException("Heure requise");
        }

        this.id = id;
        this.movie = movie;
        this.theatre = theatre;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysOfWeek = daysOfWeek;
        this.time = time;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    // === Getters ===

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

