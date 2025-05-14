package com.movieapp.api.rest.mapper;

import com.movieapp.api.rest.dto.request.CreateMovieScheduleRequest;
import com.movieapp.domain.model.*;
import com.movieapp.domain.model.MovieSchedule;
import com.movieapp.api.rest.dto.response.MovieScheduleResponse;

public class MovieScheduleRestMapper {

    public static MovieScheduleResponse toResponse(MovieSchedule s) {
        return new MovieScheduleResponse(
                s.getId(),
                s.getMovie().getTitle(),
                s.getTheatre().getName(),
                s.getStartDate(),
                s.getEndDate(),
                s.getDaysOfWeek(),
                s.getTime()
        );
    }

    public static MovieSchedule fromRequest(CreateMovieScheduleRequest req, Movie movie, Theatre theatre) {
        return new MovieSchedule(
                0,
                movie,
                theatre,
                req.getStartDate(),
                req.getEndDate(),
                req.getDaysOfWeek(),
                req.getTime(),
                null
        );
    }
}