package com.movieapp.infrastructure.persistence.repository;

import com.movieapp.domain.model.UserTheatreAssignment;
import com.movieapp.domain.repository.UserTheatreAssignmentRepository;
import com.movieapp.infrastructure.persistence.entity.UserTheatreAssignmentEntity.Id;
import com.movieapp.infrastructure.persistence.mapper.UserTheatreAssignmentMapper;
import com.movieapp.infrastructure.persistence.jpa.UserTheatreAssignmentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserTheatreAssignmentRepositoryAdapter implements UserTheatreAssignmentRepository {

    private final UserTheatreAssignmentJpaRepository jpa;

    public UserTheatreAssignmentRepositoryAdapter(UserTheatreAssignmentJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<UserTheatreAssignment> findAll() {
        return jpa.findAll().stream().map(UserTheatreAssignmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<UserTheatreAssignment> findByUserId(int userId) {
        return jpa.findByUser_Id(userId).stream().map(UserTheatreAssignmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<UserTheatreAssignment> findByTheatreId(int theatreId) {
        return jpa.findByTheatre_Id(theatreId).stream().map(UserTheatreAssignmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void assign(UserTheatreAssignment a) {
        jpa.save(UserTheatreAssignmentMapper.toEntity(a));
    }

    @Override
    public void delete(int userId, int theatreId) {
        jpa.deleteById(new Id(userId, theatreId));
    }
}
