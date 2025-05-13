package com.movieapp.domain.model;

import java.time.LocalDateTime;

public class UserTheatreAssignment {

    private final User user;
    private final Theatre theatre;
    private final LocalDateTime assignedAt;

    public UserTheatreAssignment(User user, Theatre theatre, LocalDateTime assignedAt) {
        if (user == null || theatre == null) {
            throw new IllegalArgumentException("User et Théâtre requis");
        }
        this.user = user;
        this.theatre = theatre;
        this.assignedAt = assignedAt != null ? assignedAt : LocalDateTime.now();
    }

    public User getUser() { return user; }
    public Theatre getTheatre() { return theatre; }
    public LocalDateTime getAssignedAt() { return assignedAt; }
}
