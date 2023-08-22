package com.alejandro.crud.services;

import com.alejandro.crud.entities.ProductDocument;
import com.alejandro.crud.entities.ProductRequest;
import com.alejandro.crud.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductDocument save(ProductRequest product) {
        var circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        var productToSave = ProductDocument.builder().name(product.getName()).build();
        return circuitBreaker.run(
                () -> this.saveOnMongo(productToSave), throwable -> this.sendToBroker("save", product));
    }
    public ProductDocument saveOnMongo(ProductDocument product) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
      return productRepository.save(product);
    }

    public ProductDocument sendToBroker(String topic, ProductRequest product){
        log.info("circuit breaker");
        try {
            this.kafkaTemplate.send(topic, product);
        } catch (KafkaException e) {
            log.error(e.getLocalizedMessage());
        }
        return ProductDocument.builder().name(product.getName()).build();
    }
}
