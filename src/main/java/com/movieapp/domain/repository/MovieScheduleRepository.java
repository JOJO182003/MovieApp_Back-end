package com.movieapp.domain.repository;

import com.movieapp.domain.model.MovieSchedule;

import java.util.List;
import java.util.Optional;

public interface MovieScheduleRepository {
    List<MovieSchedule> findAll();
    Optional<MovieSchedule> findById(int id);
    List<MovieSchedule> findByMovieId(int movieId);
    List<MovieSchedule> findByTheatreCity(String city);
    MovieSchedule save(MovieSchedule schedule);
    void deleteById(int id);
    boolean existsById(int id);
}
