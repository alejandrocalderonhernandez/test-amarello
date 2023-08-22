package com.alejandro.orchestrator.listeners;

import com.alejandro.orchestrator.models.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class FallbackListener {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "save", groupId = "${spring.kafka.consumer.group-id}")
    public void saveEvent(ProductRequest event) {

        log.info("Revived: " + event );
    }

}
