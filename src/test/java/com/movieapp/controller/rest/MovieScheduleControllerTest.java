package com.movieapp.controller.rest;

import com.movieapp.api.rest.controller.MovieScheduleController;
import com.movieapp.application.service.MovieScheduleService;
import com.movieapp.domain.model.MovieSchedule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.movieapp.util.TestDataFactory.scheduleInceptionMarseille;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieScheduleController.class)
@Import(MovieScheduleControllerTest.MockedServiceConfig.class)
class MovieScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieScheduleService scheduleService;

    @Test
    void should_return_schedules() throws Exception {
        MovieSchedule schedule = scheduleInceptionMarseille();

        when(scheduleService.getAll()).thenReturn(List.of(schedule));

        mockMvc.perform(get("/api/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].movieTitle").value("Film 9"))
                .andExpect(jsonPath("$[0].theatreName").value("MK2 3")); // en fonction du théâtre de la projection
    }

    @Configuration
    static class MockedServiceConfig {
        @Bean
        public MovieScheduleService scheduleService() {
            return Mockito.mock(MovieScheduleService.class);
        }
    }
}
