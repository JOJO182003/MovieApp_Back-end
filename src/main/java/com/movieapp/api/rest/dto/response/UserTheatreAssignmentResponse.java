package com.movieapp.api.rest.dto.response;

import java.time.LocalDateTime;

public class UserTheatreAssignmentResponse {

    private int userId;
    private String username;
    private int theatreId;
    private String theatreName;
    private LocalDateTime assignedAt;

    public UserTheatreAssignmentResponse(int userId, String username, int theatreId, String theatreName, LocalDateTime assignedAt) {
        this.userId = userId;
        this.username = username;
        this.theatreId = theatreId;
        this.theatreName = theatreName;
        this.assignedAt = assignedAt;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

}
