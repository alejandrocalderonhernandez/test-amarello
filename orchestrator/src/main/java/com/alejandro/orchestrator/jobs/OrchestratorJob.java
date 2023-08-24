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
        if (!InMemoryRepository.inMemoryProductsInsert.isEmpty()) {
            log.info("Insert DB");
            InMemoryRepository.inMemoryProductsInsert
                    .forEach(product ->
                        productRepository.save(
                            ProductDocument.builder().name(product.getName()).build()));
            InMemoryRepository.inMemoryProductsInsert.clear();
        }

        if (!InMemoryRepository.inMemoryProductsDelete.isEmpty()) {
            log.info("deleting DB");
            InMemoryRepository.inMemoryProductsDelete
                    .forEach(product
                            -> productRepository.deleteById(product.getId()));
            InMemoryRepository.inMemoryProductsDelete.clear();
        }

        if (!InMemoryRepository.inMemoryProductsUpdate.isEmpty()) {
            log.info("Update DB");
            InMemoryRepository.inMemoryProductsUpdate
                    .forEach(product ->
                            productRepository.save(
                                    ProductDocument.builder().id(product.getId()).name(product.getName()).build()));
            InMemoryRepository.inMemoryProductsInsert.clear();
        }
    }
}
