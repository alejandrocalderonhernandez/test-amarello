package com.alejandro.orchestrator.repositories;

import com.alejandro.orchestrator.models.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductDocument, String> {

    void deleteByName(String name);
}
