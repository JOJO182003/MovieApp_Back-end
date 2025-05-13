package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.Role;
import com.movieapp.infrastructure.persistence.entity.RoleEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleMapperTest {

    @Test
    void should_convert_role() {
        Role domain = new Role(1, "ADMIN");
        RoleEntity entity = RoleMapper.toEntity(domain);
        Role mapped = RoleMapper.toDomain(entity);

        assertEquals(domain.getName(), mapped.getName());
        assertEquals(domain.getId(), mapped.getId());
    }
}
