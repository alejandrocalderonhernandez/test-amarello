package com.alejandro.crud.services;

import com.alejandro.crud.entities.ProductDocument;
import com.alejandro.crud.entities.ProductEvent;
import com.alejandro.crud.entities.ProductEventWithId;
import com.alejandro.crud.entities.ProductRequest;
import com.alejandro.crud.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

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
                () -> this.saveOnMongo(productToSave), throwable -> this.sendToBroker("save", productToSave));
    }

    public ProductDocument update(ProductRequest product, String id) {
        var circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        var productToSave = ProductDocument.builder().name(product.getName()).id(id).build();
        return circuitBreaker.run(
                () -> this.saveOnMongo(productToSave), throwable -> this.sendToBroker("update", productToSave));
    }

    public ProductDocument delete( String id) {
        var circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        var productToDelete = productRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return circuitBreaker.run(
                () -> deleteOnMongo(id), throwable -> sendToBroker("delete", productToDelete));
    }

    public ProductDocument sendToBroker(String topic, ProductDocument product){
        log.info("circuit breaker");
        try {
            if (Objects.nonNull(product.getId())) {
                var event = ProductEventWithId.builder().id(product.getId()).name(product.getName()).build();
                this.kafkaTemplate.send(topic, event);
            } else {
                var event = ProductEvent.builder().name(product.getName()).build();
                this.kafkaTemplate.send(topic, event);
            }
        } catch (KafkaException e) {
            log.error(e.getLocalizedMessage());
        }
        return ProductDocument.builder().name(product.getName()).build();
    }

    public ProductDocument findById(String id) {
        return this.productRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    private  void simulateTimeout() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.warn("timeout");
        }
    }

    private ProductDocument saveOnMongo(ProductDocument product) {
        simulateTimeout();
        return productRepository.save(product);
    }

    private ProductDocument deleteOnMongo(String id) {
        simulateTimeout();
        this.productRepository.deleteById(id);
        return ProductDocument.builder().build();
    }
}
