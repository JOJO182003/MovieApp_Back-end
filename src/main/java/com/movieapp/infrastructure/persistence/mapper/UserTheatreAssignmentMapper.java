package com.movieapp.infrastructure.persistence.mapper;

import com.movieapp.domain.model.UserTheatreAssignment;
import com.movieapp.infrastructure.persistence.entity.UserTheatreAssignmentEntity;

public class UserTheatreAssignmentMapper {

    // Convertit une entité en un objet domaine
    public static UserTheatreAssignment toDomain(UserTheatreAssignmentEntity entity) {
        if (entity == null) {
            return null;  // Gère le cas où l'entité serait nulle
        }

        return new UserTheatreAssignment(
                UserMapper.toDomain(entity.getUser()),  // Conversion de l'utilisateur
                TheatreMapper.toDomain(entity.getTheatre()),  // Conversion du théâtre
                entity.getAssignedAt()  // Date d'affectation
        );
    }

    // Convertit un objet domaine en une entité
    public static UserTheatreAssignmentEntity toEntity(UserTheatreAssignment domain) {
        if (domain == null) {
            return null;  // Gère le cas où le domaine serait nul
        }

        var entity = new UserTheatreAssignmentEntity();

        // Conversion inverse de l'utilisateur et du théâtre
        entity.setUser(UserMapper.toEntity(domain.getUser()));
        entity.setTheatre(TheatreMapper.toEntity(domain.getTheatre()));
        entity.setAssignedAt(domain.getAssignedAt());

        // Définition de l'ID composé dans l'entité (si nécessaire)
        entity.setId(new UserTheatreAssignmentEntity.Id(
                domain.getUser().getId(),  // ID de l'utilisateur
                domain.getTheatre().getId()  // ID du théâtre
        ));

        return entity;
    }
}

