package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.MovieSchedule;
import com.movieapp.infrastructure.persistence.entity.MovieScheduleEntity;

public class MovieScheduleMapper {

    public static MovieSchedule toDomain(MovieScheduleEntity entity) {
        return new MovieSchedule(
                entity.getId(),
                MovieMapper.toDomain(entity.getMovie()),
                TheatreMapper.toDomain(entity.getTheatre()),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getDaysOfWeek(),
                entity.getTime(),
                entity.getCreatedAt()
        );
    }

    public static MovieScheduleEntity toEntity(MovieSchedule domain) {
        MovieScheduleEntity entity = new MovieScheduleEntity();
        entity.setId(domain.getId());
        entity.setMovie(MovieMapper.toEntity(domain.getMovie()));
        entity.setTheatre(TheatreMapper.toEntity(domain.getTheatre()));
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setDaysOfWeek(domain.getDaysOfWeek());
        entity.setTime(domain.getTime());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
}
