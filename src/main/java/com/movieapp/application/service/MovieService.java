package com.movieapp.application.service;

import com.movieapp.domain.model.Movie;
import com.movieapp.domain.repository.MovieRepository;
import com.movieapp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository repo;

    public MovieService(MovieRepository repo) {
        this.repo = repo;
    }

    public List<Movie> getAll() {
        return repo.findAll();
    }

    public Movie getById(int id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Film non trouvé"));
    }

    public Movie create(Movie movie) {
        return repo.save(movie);
    }

    public void delete(int id) {
        if (!repo.existsById(id)) throw new NotFoundException("Film introuvable");
        repo.deleteById(id);
    }
}
