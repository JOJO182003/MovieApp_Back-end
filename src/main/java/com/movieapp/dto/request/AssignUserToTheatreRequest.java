package com.movieapp.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * Requête pour assigner un utilisateur à un théâtre spécifique.
 */
public class AssignUserToTheatreRequest {

    @NotNull(message = "L'identifiant utilisateur est requis")
    private Integer userId;

    @NotNull(message = "L'identifiant du théâtre est requis")
    private Integer theatreId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Integer theatreId) {
        this.theatreId = theatreId;
    }
}

