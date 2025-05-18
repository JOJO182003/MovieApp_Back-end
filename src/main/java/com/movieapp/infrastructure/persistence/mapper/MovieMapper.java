package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.Movie;
import com.movieapp.infrastructure.persistence.entity.MovieEntity;

public class MovieMapper {

    public static Movie toDomain(MovieEntity entity) {
        return new Movie(
                entity.getId(),
                entity.getTitle(),
                entity.getSynopsis(),
                entity.getDurationMinutes(),
                entity.getLanguage(),
                entity.getDirector(),
                entity.getMinAge(),
                entity.getReleaseDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getThumbnail()
        );
    }

    public static MovieEntity toEntity(Movie domain) {
        MovieEntity entity = new MovieEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setSynopsis(domain.getSynopsis());
        entity.setDurationMinutes(domain.getDurationMinutes());
        entity.setLanguage(domain.getLanguage());
        entity.setDirector(domain.getDirector());
        entity.setMinAge(domain.getMinAge());
        entity.setReleaseDate(domain.getReleaseDate());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setThumbnail(domain.getThumbnail());
        return entity;
    }
}
