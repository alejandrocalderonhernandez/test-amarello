package com.alejandro.orchestrator.repositories;

import com.alejandro.orchestrator.models.ProductEvent;

import java.util.HashSet;
import java.util.Set;

public class InMemoryRepository {

    public static final Set<ProductEvent> inMemoryProductsUpsert = new HashSet<>();
    public static final Set<ProductEvent> inMemoryProductsDelete = new HashSet<>();
}
