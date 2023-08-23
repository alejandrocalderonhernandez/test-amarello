package com.alejandro.orchestrator.jobs;

import com.alejandro.orchestrator.models.ProductDocument;
import com.alejandro.orchestrator.repositories.InMemoryRepository;
import com.alejandro.orchestrator.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class OrchestratorJob {

    private final ProductRepository productRepository;

    @Scheduled(cron = "0 */2 * * * *")
    public void upsertMongo() {
        System.out.println("Start job");
        if (!InMemoryRepository.inMemoryProductsUpsert.isEmpty()) {
            log.info("Upsert DB");
            InMemoryRepository.inMemoryProductsUpsert
                    .forEach(product ->
                        productRepository.save(
                            ProductDocument.builder().name(product.getName()).build()));
            InMemoryRepository.inMemoryProductsUpsert.clear();
        }

        if (!InMemoryRepository.inMemoryProductsDelete.isEmpty()) {
            log.info("deleting DB");
            System.out.println(InMemoryRepository.inMemoryProductsUpsert);
            InMemoryRepository.inMemoryProductsUpsert
                    .forEach(product
                            -> productRepository.deleteByName(product.getName()));
            InMemoryRepository.inMemoryProductsUpsert.clear();
        }

    }
}
