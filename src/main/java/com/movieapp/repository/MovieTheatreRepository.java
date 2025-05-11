package com.movieapp.repository;

import com.movieapp.model.MovieTheatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieTheatreRepository extends JpaRepository<MovieTheatre, Integer> {
    List<MovieTheatre> findByTheatre_CityIgnoreCase(String city);
    List<MovieTheatre> findByMovie_Id(int movieId);
}
