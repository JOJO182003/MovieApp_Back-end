package com.movieapp.infrastructure.persistence.springdata;

import com.movieapp.infrastructure.persistence.entity.TheatreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheatreJpaRepository extends JpaRepository<TheatreEntity, Integer> {
}
