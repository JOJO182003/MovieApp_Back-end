package com.movieapp.api.rest.mapper;

import com.movieapp.domain.model.User;
import com.movieapp.api.rest.dto.request.CreateUserRequest;
import com.movieapp.api.rest.dto.response.UserResponse;

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

    public static User fromRequest(CreateUserRequest req) {
        return new User(
                0,
                req.getUsername(),
                req.getEmail(),
                req.getPassword(), // le hash sera appliqué ensuite dans le service
                null,              // role sera injecté après récupération via roleId
                List.of(),         // initialement vide, à peupler si assignations
                null,
                null
        );
    }
}
