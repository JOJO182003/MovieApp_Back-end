package com.movieapp.controller.rest;

import com.movieapp.api.rest.controller.MovieController;
import com.movieapp.api.rest.dto.request.CreateMovieRequest;
import com.movieapp.application.service.MovieService;
import com.movieapp.domain.model.Movie;
import com.movieapp.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@Import(MovieControllerTest.SecurityOverrideConfig.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Test
    void should_return_all_movies() throws Exception {
        Movie movie = TestDataFactory.movieInception();
        when(movieService.getAll()).thenReturn(List.of(movie));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(movie.getId()))
                .andExpect(jsonPath("$[0].thumbnail").value(movie.getThumbnail()));
    }

    @Test
    void should_return_single_movie() throws Exception {
        Movie movie = TestDataFactory.movieInception();
        when(movieService.getById(9)).thenReturn(movie);

        mockMvc.perform(get("/api/movies/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movie.getId()))
                .andExpect(jsonPath("$.thumbnail").value(movie.getThumbnail()));
    }

    @Test
    @WithMockUser(username = "owner", roles = {"CINEMA_OWNER"})
    void should_create_movie() throws Exception {
        CreateMovieRequest req = TestDataFactory.createMovieRequest1();
        Movie movie = TestDataFactory.movieInception();

        when(movieService.create(Mockito.any())).thenReturn(movie);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                          "title": "Inception",
                          "description": "A mind-bending thriller",
                          "durationMinutes": 148,
                          "language": "English",
                          "director": "Christopher Nolan",
                          "releaseDate": "2010-07-16",
                          "thumbnail": "https://example.com/inception.jpg"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(movie.getId()));
    }

    @Test
    void should_deny_create_movie_without_auth() throws Exception {
        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "title": "Inception",
                                "description": "A mind-bending thriller",
                                "durationMinutes": 148
                            }
                        """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "owner", roles = {"CINEMA_OWNER"})
    void should_delete_movie() throws Exception {
        mockMvc.perform(delete("/api/movies/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(movieService).delete(1);
    }

    @Test
    void should_deny_delete_movie_without_auth() throws Exception {
        mockMvc.perform(delete("/api/movies/1"))
                .andExpect(status().isUnauthorized());
    }

    @TestConfiguration
    static class SecurityOverrideConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.GET, "/api/movies/**").permitAll()
                            .requestMatchers("/api/movies/**").hasRole("CINEMA_OWNER")
                            .anyRequest().permitAll()
                    )
                    .httpBasic(withDefaults())
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }

        @Bean
        public MovieService movieService() {
            return Mockito.mock(MovieService.class);
        }
    }
}
