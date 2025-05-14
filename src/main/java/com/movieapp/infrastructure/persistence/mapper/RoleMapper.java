package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.Role;
import com.movieapp.infrastructure.persistence.entity.RoleEntity;

public class RoleMapper {

    public static Role toDomain(RoleEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }

    public static RoleEntity toEntity(Role domain) {
        RoleEntity entity = new RoleEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        return entity;
    }
}
