package com.movieapp.domain.repository;

import com.movieapp.domain.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    List<Movie> findAll();
    Optional<Movie> findById(int id);
    Movie save(Movie movie);
    void deleteById(int id);
    boolean existsById(int id);
}
