package com.movieapp.infrastructure.persistence.jpa;

import com.movieapp.infrastructure.persistence.entity.RoleEntity;
import com.movieapp.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    @EntityGraph(attributePaths = {"role", "theatres"})
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findAllByRole(RoleEntity role);
    Optional<UserEntity> findByEmail(String email);
}
