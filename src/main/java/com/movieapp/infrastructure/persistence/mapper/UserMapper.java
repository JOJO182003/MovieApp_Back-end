package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.User;
import com.movieapp.infrastructure.persistence.entity.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPasswordHash(),
                RoleMapper.toDomain(entity.getRole()),
                entity.getTheatres().stream()
                        .map(TheatreMapper::toDomain)
                        .collect(Collectors.toList()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setRole(RoleMapper.toEntity(domain.getRole()));
        entity.setTheatres(domain.getTheatres().stream()
                .map(TheatreMapper::toEntity)
                .collect(Collectors.toList()));
        return entity;
    }
}
