package com.movieapp.domain.repository;

import com.movieapp.domain.model.Theatre;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository {
    List<Theatre> findAll();
    Optional<Theatre> findById(int id);
    Theatre save(Theatre theatre);
    void deleteById(int id);
    boolean existsById(int id);
}
