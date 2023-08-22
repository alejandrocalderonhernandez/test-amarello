package com.alejandro.crud.repositories;

import com.alejandro.crud.entities.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductDocument, String> {
}
