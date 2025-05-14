package com.movieapp.infrastructure.persistence.jpa;

import com.movieapp.infrastructure.persistence.entity.MovieScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieScheduleJpaRepository extends JpaRepository<MovieScheduleEntity, Integer> {
    List<MovieScheduleEntity> findByMovie_Id(int movieId);
    List<MovieScheduleEntity> findByTheatre_CityIgnoreCase(String city);
}
