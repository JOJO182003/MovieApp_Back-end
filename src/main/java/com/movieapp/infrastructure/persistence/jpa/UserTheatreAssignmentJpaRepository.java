package com.movieapp.infrastructure.persistence.jpa;

import com.movieapp.infrastructure.persistence.entity.UserTheatreAssignmentEntity;
import com.movieapp.infrastructure.persistence.entity.UserTheatreAssignmentEntity.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTheatreAssignmentJpaRepository extends JpaRepository<UserTheatreAssignmentEntity, Id> {
    List<UserTheatreAssignmentEntity> findByUser_Id(int userId);
    List<UserTheatreAssignmentEntity> findByTheatre_Id(int theatreId);
}
