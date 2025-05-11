package com.movieapp.service;

import com.movieapp.exception.ResourceNotFoundException;
import com.movieapp.model.Movie;
import com.movieapp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(int id) {
        return movieRepository.findById(id);
    }

    // Version avec exception si le film n'existe pas
    public Movie getMovieByIdOrThrow(int id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film introuvable avec l'ID : " + id));
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(int id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : aucun film avec l'ID : " + id);
        }
        movieRepository.deleteById(id);
    }
}
