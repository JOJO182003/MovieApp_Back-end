package com.movieapp.infrastructure.persistence.repository;

import com.movieapp.domain.model.MovieSchedule;
import com.movieapp.domain.repository.MovieScheduleRepository;
import com.movieapp.infrastructure.persistence.mapper.MovieScheduleMapper;
import com.movieapp.infrastructure.persistence.jpa.MovieScheduleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieScheduleRepositoryAdapter implements MovieScheduleRepository {

    private final MovieScheduleJpaRepository jpa;

    public MovieScheduleRepositoryAdapter(MovieScheduleJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<MovieSchedule> findAll() {
        return jpa.findAll().stream().map(MovieScheduleMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<MovieSchedule> findById(int id) {
        return jpa.findById(id).map(MovieScheduleMapper::toDomain);
    }

    @Override
    public List<MovieSchedule> findByMovieId(int movieId) {
        return jpa.findByMovie_Id(movieId).stream().map(MovieScheduleMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<MovieSchedule> findByTheatreCity(String city) {
        return jpa.findByTheatre_CityIgnoreCase(city).stream().map(MovieScheduleMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public MovieSchedule save(MovieSchedule s) {
        return MovieScheduleMapper.toDomain(jpa.save(MovieScheduleMapper.toEntity(s)));
    }

    @Override
    public void deleteById(int id) {
        jpa.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return jpa.existsById(id);
    }
}
