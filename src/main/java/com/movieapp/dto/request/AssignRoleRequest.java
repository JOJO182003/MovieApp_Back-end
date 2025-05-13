package com.movieapp.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Requête pour assigner un rôle à un utilisateur existant.
 */
public class AssignRoleRequest {

    @NotNull(message = "L'identifiant utilisateur est requis")
    private Integer userId;

    @NotNull(message = "L'identifiant du rôle est requis")
    private Integer roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
