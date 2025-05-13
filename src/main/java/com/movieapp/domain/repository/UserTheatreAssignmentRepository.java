package com.movieapp.domain.repository;

import com.movieapp.domain.model.UserTheatreAssignment;

import java.util.List;

public interface UserTheatreAssignmentRepository {
    List<UserTheatreAssignment> findAll();
    List<UserTheatreAssignment> findByUserId(int userId);
    List<UserTheatreAssignment> findByTheatreId(int theatreId);
    void assign(UserTheatreAssignment assignment);
    void delete(int userId, int theatreId);
}
