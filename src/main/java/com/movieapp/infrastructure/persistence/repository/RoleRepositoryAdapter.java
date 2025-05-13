package com.movieapp.infrastructure.persistence.repository;

import com.movieapp.domain.model.Role;
import com.movieapp.domain.repository.RoleRepository;
import com.movieapp.infrastructure.persistence.mapper.RoleMapper;
import com.movieapp.infrastructure.persistence.jpa.RoleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryAdapter implements RoleRepository {

    private final RoleJpaRepository jpa;

    public RoleRepositoryAdapter(RoleJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Role> findAll() {
        return jpa.findAll().stream().map(RoleMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Role> findById(int id) {
        return jpa.findById(id).map(RoleMapper::toDomain);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpa.findByName(name).map(RoleMapper::toDomain);
    }

    @Override
    public Role save(Role role) {
        return RoleMapper.toDomain(jpa.save(RoleMapper.toEntity(role)));
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
