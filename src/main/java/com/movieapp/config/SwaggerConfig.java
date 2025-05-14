package com.movieapp.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Movie App API")
                        .description("Documentation de l'API de gestion des utilisateurs, films, théâtres et projections.")
                        .version("1.0.0")
        );
    }
}
