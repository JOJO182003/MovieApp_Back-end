package com.movieapp.api.rest.mapper;

import com.movieapp.domain.model.Movie;
import com.movieapp.api.rest.dto.request.CreateMovieRequest;
import com.movieapp.api.rest.dto.response.MovieResponse;

public class MovieRestMapper {

    public static MovieResponse toResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getLanguage(),
                movie.getDirector(),
                movie.getDurationMinutes(),
                movie.getReleaseDate()
        );
    }

    public static Movie fromRequest(CreateMovieRequest req) {
        return new Movie(
                0,
                req.getTitle(),
                req.getSynopsis(),
                req.getDurationMinutes(),
                req.getLanguage(),
                req.getDirector(),
                req.getMinAge(),
                req.getReleaseDate(),
                null,
                null
        );
    }
}
