package com.movieapp.service;

import com.movieapp.exception.ResourceNotFoundException;
import com.movieapp.model.MovieTheatre;
import com.movieapp.repository.MovieTheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieTheatreService {

    private final MovieTheatreRepository repository;

    // Doublon de getAllProjections, gardé pour compatibilité
    public List<MovieTheatre> findAll() {
        return repository.findAll();
    }

    public List<MovieTheatre> getAllProjections() {
        return repository.findAll();
    }

    // Retourne un Optional comme avant
    public Optional<MovieTheatre> getProjectionById(int id) {
        return repository.findById(id);
    }

    // Version stricte avec exception
    public MovieTheatre getProjectionByIdOrThrow(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projection introuvable (ID : " + id + ")"));
    }

    public List<MovieTheatre> findByCity(String city) {
        return repository.findByTheatre_CityIgnoreCase(city);
    }

    // Doublon de saveProjection, gardé
    public MovieTheatre save(MovieTheatre mt) {
        return repository.save(mt);
    }

    public MovieTheatre saveProjection(MovieTheatre projection) {
        return repository.save(projection);
    }

    public void deleteProjection(int id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : projection non trouvée (ID : " + id + ")");
        }
        repository.deleteById(id);
    }
}
