package com.movieapp.domain.repository;

import com.movieapp.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    List<User> findAllCinemaOwners();
    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save(User user);
    void deleteById(int id);
    boolean existsById(int id);
}
