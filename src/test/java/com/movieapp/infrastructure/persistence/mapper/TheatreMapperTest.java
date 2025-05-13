package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.Theatre;
import com.movieapp.infrastructure.persistence.entity.TheatreEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TheatreMapperTest {

    @Test
    void should_map_theatre_domain_to_entity_and_back() {
        Theatre domain = new Theatre(1, "Pathé", "Paris", "Rue A");

        TheatreEntity entity = TheatreMapper.toEntity(domain);
        Theatre mapped = TheatreMapper.toDomain(entity);

        assertEquals(domain.getCity(), mapped.getCity());
        assertEquals(domain.getAddress(), mapped.getAddress());
    }
}
