package com.movieapp.application.service;

import com.movieapp.domain.model.Theatre;
import com.movieapp.domain.repository.TheatreRepository;
import com.movieapp.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {

    private final TheatreRepository repo;

    public TheatreService(TheatreRepository repo) {
        this.repo = repo;
    }

    public List<Theatre> getAll() {
        return repo.findAll();
    }

    public Theatre getById(int id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Théâtre non trouvé"));
    }

    public Theatre create(Theatre theatre) {
        return repo.save(theatre);
    }

    public void delete(int id) {
        if (!repo.existsById(id)) throw new NotFoundException("Théâtre introuvable");
        repo.deleteById(id);
    }
}
