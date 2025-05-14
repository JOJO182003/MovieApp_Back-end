package com.movieapp.api.rest.dto.response;

import java.time.*;

public class MovieScheduleResponse {

    private int id;
    private String movieTitle;
    private String theatreName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String daysOfWeek;
    private LocalTime time;

    public MovieScheduleResponse(int id, String movieTitle, String theatreName,
                                 LocalDate startDate, LocalDate endDate, String daysOfWeek, LocalTime time) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.theatreName = theatreName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysOfWeek = daysOfWeek;
        this.time = time;
    }

    // Getters
}
