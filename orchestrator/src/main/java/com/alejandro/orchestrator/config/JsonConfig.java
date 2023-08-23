package com.alejandro.orchestrator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
