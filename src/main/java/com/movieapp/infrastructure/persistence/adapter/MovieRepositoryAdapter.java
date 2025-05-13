package com.movieapp.infrastructure.persistence.adapter;

import com.movieapp.domain.model.Movie;
import com.movieapp.domain.repository.MovieRepository;
import com.movieapp.infrastructure.persistence.entity.MovieEntity;
import com.movieapp.infrastructure.persistence.mapper.MovieMapper;
import com.movieapp.infrastructure.persistence.springdata.MovieJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieRepositoryAdapter implements MovieRepository {

    private final MovieJpaRepository jpa;

    public MovieRepositoryAdapter(MovieJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Movie> findAll() {
        return jpa.findAll().stream().map(MovieMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Movie> findById(int id) {
        return jpa.findById(id).map(MovieMapper::toDomain);
    }

    @Override
    public Movie save(Movie movie) {
        return MovieMapper.toDomain(jpa.save(MovieMapper.toEntity(movie)));
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
