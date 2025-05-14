package com.movieapp.controller.rest;

import com.movieapp.api.rest.controller.MovieController;
import com.movieapp.application.service.MovieService;
import com.movieapp.domain.model.Movie;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.movieapp.util.TestDataFactory.movieInception;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@Import(MovieControllerTest.MockedServiceConfig.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Test
    void should_return_all_movies() throws Exception {
        Movie movie = movieInception(); // from TestDataFactory

        when(movieService.getAll()).thenReturn(List.of(movie));

        mockMvc.perform(get("/api/movies").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(movie.getId()))
                .andExpect(jsonPath("$[0].title").value(movie.getTitle()))
                .andExpect(jsonPath("$[0].language").value(movie.getLanguage()))
                .andExpect(jsonPath("$[0].director").value(movie.getDirector()))
                .andExpect(jsonPath("$[0].durationMinutes").value(movie.getDurationMinutes()))
                .andExpect(jsonPath("$[0].releaseDate").value(movie.getReleaseDate().toString()));
    }

    @Configuration
    static class MockedServiceConfig {
        @Bean
        public MovieService movieService() {
            return Mockito.mock(MovieService.class);
        }
    }
}
