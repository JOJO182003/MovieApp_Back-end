package com.movieapp.api.rest.mapper;

import com.movieapp.domain.model.UserTheatreAssignment;
import com.movieapp.api.rest.dto.response.UserTheatreAssignmentResponse;

public class UserTheatreAssignmentRestMapper {

    public static UserTheatreAssignmentResponse toResponse(UserTheatreAssignment assignment) {
        return new UserTheatreAssignmentResponse(
                assignment.getUser().getId(),
                assignment.getUser().getUsername(),
                assignment.getTheatre().getId(),
                assignment.getTheatre().getName(),
                assignment.getAssignedAt()
        );
    }
}
