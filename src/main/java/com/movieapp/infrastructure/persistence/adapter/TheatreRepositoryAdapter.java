package com.movieapp.infrastructure.persistence.adapter;

import com.movieapp.domain.model.Theatre;
import com.movieapp.domain.repository.TheatreRepository;
import com.movieapp.infrastructure.persistence.entity.TheatreEntity;
import com.movieapp.infrastructure.persistence.mapper.TheatreMapper;
import com.movieapp.infrastructure.persistence.springdata.TheatreJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TheatreRepositoryAdapter implements TheatreRepository {

    private final TheatreJpaRepository jpa;

    public TheatreRepositoryAdapter(TheatreJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Theatre> findAll() {
        return jpa.findAll().stream().map(TheatreMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Theatre> findById(int id) {
        return jpa.findById(id).map(TheatreMapper::toDomain);
    }

    @Override
    public Theatre save(Theatre theatre) {
        return TheatreMapper.toDomain(jpa.save(TheatreMapper.toEntity(theatre)));
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
