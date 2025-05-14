package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.*;
import com.movieapp.infrastructure.persistence.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void should_convert_user_entity() {
        RoleEntity role = new RoleEntity();
        role.setId(1);
        role.setName("USER");

        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setUsername("john");
        entity.setEmail("john@example.com");
        entity.setPasswordHash("hash");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setRole(role);

        User domain = UserMapper.toDomain(entity);
        assertEquals("john", domain.getUsername());

        UserEntity mappedBack = UserMapper.toEntity(domain);
        assertEquals("john@example.com", mappedBack.getEmail());
    }
}
