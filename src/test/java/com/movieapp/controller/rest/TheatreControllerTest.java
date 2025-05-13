package com.movieapp.controller.rest;

import com.movieapp.api.rest.controller.TheatreController;
import com.movieapp.application.service.TheatreService;
import com.movieapp.domain.model.Theatre;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.movieapp.util.TestDataFactory.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TheatreController.class)
@Import(TheatreControllerTest.MockedServiceConfig.class)
class TheatreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TheatreService theatreService;

    @Test
    void should_return_theatres() throws Exception {
        Theatre theatre = theatreLyon1();

        when(theatreService.getAll()).thenReturn(List.of(theatre));

        mockMvc.perform(get("/api/theatres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gaumont 1"))
                .andExpect(jsonPath("$[0].city").value("Lyon"))
                .andExpect(jsonPath("$[0].address").value("94 rue de la Ville, Lyon"));
    }

    @Configuration
    static class MockedServiceConfig {
        @Bean
        public TheatreService theatreService() {
            return Mockito.mock(TheatreService.class);
        }
    }
}
