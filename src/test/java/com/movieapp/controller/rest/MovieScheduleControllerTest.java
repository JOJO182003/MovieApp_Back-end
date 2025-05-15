package com.movieapp.controller.rest;

import com.movieapp.api.rest.controller.MovieScheduleController;
import com.movieapp.api.rest.dto.request.CreateMovieScheduleRequest;
import com.movieapp.application.service.MovieScheduleService;
import com.movieapp.application.service.MovieService;
import com.movieapp.application.service.TheatreService;
import com.movieapp.domain.model.Movie;
import com.movieapp.domain.model.MovieSchedule;
import com.movieapp.domain.model.Theatre;
import com.movieapp.util.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.movieapp.util.TestDataFactory.scheduleInceptionMarseille;
import static org.mockito.Mockito.when;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieScheduleController.class)
@Import(MovieScheduleControllerTest.SecurityOverrideConfig.class)
class MovieScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieScheduleService scheduleService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheatreService theatreService;

    @Test
    void should_return_all_schedules() throws Exception {
        MovieSchedule schedule = TestDataFactory.scheduleInceptionMarseille();
        when(scheduleService.getAll()).thenReturn(List.of(schedule));

        mockMvc.perform(get("/api/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(schedule.getId()));
    }

    @Test
    void should_return_single_schedule() throws Exception {
        MovieSchedule schedule = TestDataFactory.scheduleInceptionMarseille();
        when(scheduleService.getById(1)).thenReturn(schedule);

        mockMvc.perform(get("/api/schedules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schedule.getId()));
    }

    @Test
    @WithMockUser(username = "owner", roles = {"CINEMA_OWNER"})
    void should_create_schedule() throws Exception {
        CreateMovieScheduleRequest req = TestDataFactory.createMovieScheduleRequest1();
        Movie movie = TestDataFactory.movieInception();
        Theatre theatre = TestDataFactory.theatreLyon1();
        MovieSchedule schedule = TestDataFactory.scheduleInceptionMarseille();

        when(movieService.getById(req.getMovieId())).thenReturn(movie);
        when(theatreService.getById(req.getTheatreId())).thenReturn(theatre);
        when(scheduleService.create(Mockito.any())).thenReturn(schedule);

        mockMvc.perform(post("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
            {
                "movieId": 1,
                "theatreId": 1,
                "startTime": "2025-06-01T20:00:00",
                "endDate": "2025-06-30",
                "daysOfWeek": "MONDAY,TUESDAY",
                "time": "20:00"
            }
        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(schedule.getId()));

    }

    @Test
    void should_deny_create_schedule_without_auth() throws Exception {
        mockMvc.perform(post("/api/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "movieId": 1,
                                "theatreId": 1,
                                "startTime": "2025-06-01T20:00:00",
                                "price": 10.5
                            }
                        """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "owner", roles = {"CINEMA_OWNER"})
    void should_delete_schedule() throws Exception {
        mockMvc.perform(delete("/api/schedules/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(scheduleService).delete(1);
    }

    @Test
    void should_deny_delete_schedule_without_auth() throws Exception {
        mockMvc.perform(delete("/api/schedules/1"))
                .andExpect(status().isUnauthorized());
    }

    @TestConfiguration
    static class SecurityOverrideConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.GET, "/api/schedules/**").permitAll()
                            .requestMatchers("/api/schedules/**").hasRole("CINEMA_OWNER")
                            .anyRequest().permitAll()
                    )
                    .httpBasic(withDefaults())
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }

        @Bean
        public MovieScheduleService scheduleService() {
            return Mockito.mock(MovieScheduleService.class);
        }

        @Bean
        public MovieService movieService() {
            return Mockito.mock(MovieService.class);
        }

        @Bean
        public TheatreService theatreService() {
            return Mockito.mock(TheatreService.class);
        }
    }
}
