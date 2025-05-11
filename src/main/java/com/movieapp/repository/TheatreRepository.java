package com.movieapp.repository;

import com.movieapp.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
    // Optional<Theatre> findByNameAndCity(String name, String city);
}
