package com.movieticket.onlinemovieticketreservationmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Apply to all endpoints (/api/movies, /api/auth, etc.)
                        .allowedOrigins("http://localhost:63342") // Your frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
