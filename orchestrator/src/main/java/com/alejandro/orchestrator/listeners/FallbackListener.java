package com.alejandro.orchestrator.listeners;

import com.alejandro.orchestrator.models.ProductEvent;
import com.alejandro.orchestrator.repositories.InMemoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class FallbackListener {

    @KafkaListener(topics = {"save", "update"}, groupId = "${spring.kafka.consumer.group-id}")
    public void UpsertEvent(ProductEvent event) {
        InMemoryRepository.inMemoryProductsUpsert.add(event);
        log.info("Revived upsert: " + event );
    }

    @KafkaListener(topics = "delete", groupId = "${spring.kafka.consumer.group-id}")
    public void deleteEvent(ProductEvent event) {
        InMemoryRepository.inMemoryProductsDelete.add(event);
        log.info("Revived delete: " + event );
    }

}
