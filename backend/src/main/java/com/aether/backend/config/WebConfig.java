package com.aether.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configurazione CORS del backend.
 *
 * Il frontend Angular gira su localhost:4200, questo backend su localhost:8080:
 * per il browser sono "origini" diverse e senza questo permesso bloccherebbe
 * ogni chiamata con errore "blocked by CORS policy".
 * Questa classe è quindi il punto di collegamento frontend -> backend.
 *
 * [HEL-501] Apertura incidente operativo.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Autorizza il frontend Angular a chiamare tutte le rotte /api/**
     * con i verbi HTTP usati dall'applicazione.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");
    }
}