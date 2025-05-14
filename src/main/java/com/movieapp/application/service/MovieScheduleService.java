package com.movieapp.application.service;

import com.movieapp.domain.model.MovieSchedule;
import com.movieapp.domain.repository.MovieScheduleRepository;
import com.movieapp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieScheduleService {

    private final MovieScheduleRepository repo;

    public MovieScheduleService(MovieScheduleRepository repo) {
        this.repo = repo;
    }

    public List<MovieSchedule> getAll() {
        return repo.findAll();
    }

    public MovieSchedule getById(int id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Projection non trouvée"));
    }

    public MovieSchedule create(MovieSchedule schedule) {
        return repo.save(schedule);
    }

    public void delete(int id) {
        if (!repo.existsById(id)) throw new NotFoundException("Projection non trouvée");
        repo.deleteById(id);
    }

    public List<MovieSchedule> getByCity(String city) {
        return repo.findByTheatreCity(city);
    }
}
