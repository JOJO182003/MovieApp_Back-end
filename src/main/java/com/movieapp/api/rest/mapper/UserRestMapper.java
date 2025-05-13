package com.movieapp.api.rest.mapper;

import com.movieapp.api.rest.dto.request.CreateUserRequest;
import com.movieapp.api.rest.dto.response.UserResponse;
import com.movieapp.domain.model.Role;
import com.movieapp.domain.model.User;

import java.util.List;

public class UserRestMapper {

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName()
        );
    }

    /**
     * Crée un utilisateur à partir d'une requête de création et du rôle associé.
     * Évite les utilisateurs incomplets et respecte les règles métier.
     */
    public static User fromRequest(CreateUserRequest req, Role role) {
        return new User(
                0,
                req.getUsername(),
                req.getEmail(),
                req.getPassword(), // hash sera appliqué dans le service
                role,              // jamais null ici
                List.of(),         // pas d'assignation initiale
                null,
                null
        );
    }
}

