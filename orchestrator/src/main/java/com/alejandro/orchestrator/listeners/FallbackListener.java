package com.alejandro.orchestrator.listeners;

import com.alejandro.orchestrator.models.ProductEvent;
import com.alejandro.orchestrator.models.ProductEventWithId;
import com.alejandro.orchestrator.repositories.InMemoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class FallbackListener {

    @KafkaListener(topics = "save", groupId = "${spring.kafka.consumer.group-id}")
    public void insertEvent(ProductEvent event) {
        InMemoryRepository.inMemoryProductsInsert.add(event);
        log.info("Revived upsert: " + event );
    }

    @KafkaListener(topics =  "update", groupId = "${spring.kafka.consumer.group-id}")
    public void updateEvent(ProductEventWithId event) {
        InMemoryRepository.inMemoryProductsUpdate.add(event);
        log.info("Revived upsert: " + event );
    }

    @KafkaListener(topics = "delete", groupId = "${spring.kafka.consumer.group-id}")
    public void deleteEvent(ProductEventWithId event) {
        InMemoryRepository.inMemoryProductsDelete.add(event);
        log.info("Revived delete: " + event );
    }

}
