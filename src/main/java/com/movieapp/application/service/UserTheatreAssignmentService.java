package com.movieapp.application.service;

import com.movieapp.domain.model.*;
import com.movieapp.domain.repository.*;
import com.movieapp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserTheatreAssignmentService {

    private final UserTheatreAssignmentRepository assignmentRepo;
    private final UserRepository userRepo;
    private final TheatreRepository theatreRepo;

    public UserTheatreAssignmentService(UserTheatreAssignmentRepository assignmentRepo, UserRepository userRepo, TheatreRepository theatreRepo) {
        this.assignmentRepo = assignmentRepo;
        this.userRepo = userRepo;
        this.theatreRepo = theatreRepo;
    }

    public List<UserTheatreAssignment> getAll() {
        return assignmentRepo.findAll();
    }

    public void assignUserToTheatre(int userId, int theatreId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));
        Theatre theatre = theatreRepo.findById(theatreId).orElseThrow(() -> new NotFoundException("Théâtre non trouvé"));

        UserTheatreAssignment assignment = new UserTheatreAssignment(user, theatre, LocalDateTime.now());
        assignmentRepo.assign(assignment);
    }

    public void removeAssignment(int userId, int theatreId) {
        assignmentRepo.delete(userId, theatreId);
    }

    public List<UserTheatreAssignment> getByUser(int userId) {
        return assignmentRepo.findByUserId(userId);
    }

    public List<UserTheatreAssignment> getByTheatre(int theatreId) {
        return assignmentRepo.findByTheatreId(theatreId);
    }
}
