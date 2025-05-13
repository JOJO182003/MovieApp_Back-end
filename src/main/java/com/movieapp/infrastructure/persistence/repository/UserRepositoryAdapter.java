package com.movieapp.infrastructure.persistence.repository;

import com.movieapp.domain.model.User;
import com.movieapp.domain.repository.UserRepository;
import com.movieapp.infrastructure.persistence.mapper.UserMapper;
import com.movieapp.infrastructure.persistence.jpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpa;

    public UserRepositoryAdapter(UserJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<User> findAll() {
        return jpa.findAll().stream().map(UserMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(int id) {
        return jpa.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpa.findByUsername(username).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return UserMapper.toDomain(jpa.save(UserMapper.toEntity(user)));
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
