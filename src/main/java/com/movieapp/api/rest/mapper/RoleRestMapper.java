package com.movieapp.api.rest.mapper;

import com.movieapp.domain.model.Role;
import com.movieapp.api.rest.dto.request.CreateRoleRequest;
import com.movieapp.api.rest.dto.response.RoleResponse;

public class RoleRestMapper {

    public static RoleResponse toResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName()
        );
    }

    public static Role fromRequest(CreateRoleRequest req) {
        return new Role(0, req.getName());
    }
}
