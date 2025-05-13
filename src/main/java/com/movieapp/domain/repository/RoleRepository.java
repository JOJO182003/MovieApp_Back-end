package com.movieapp.domain.repository;

import com.movieapp.domain.model.Role;
import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<Role> findAll();
    Optional<Role> findById(int id);
    Optional<Role> findByName(String name);
    Role save(Role role);
    void deleteById(int id);
    boolean existsById(int id);
}
