package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.Theatre;
import com.movieapp.infrastructure.persistence.entity.TheatreEntity;

public class TheatreMapper {

    public static Theatre toDomain(TheatreEntity entity) {
        return new Theatre(entity.getId(), entity.getName(), entity.getCity(), entity.getAddress());
    }

    public static TheatreEntity toEntity(Theatre theatre) {
        TheatreEntity entity = new TheatreEntity();
        entity.setId(theatre.getId());
        entity.setName(theatre.getName());
        entity.setCity(theatre.getCity());
        entity.setAddress(theatre.getAddress());
        return entity;
    }
}
