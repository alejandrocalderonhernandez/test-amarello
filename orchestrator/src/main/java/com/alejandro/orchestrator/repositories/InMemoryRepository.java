package com.alejandro.orchestrator.repositories;

import com.alejandro.orchestrator.models.ProductEvent;
import com.alejandro.orchestrator.models.ProductEventWithId;

import java.util.HashSet;
import java.util.Set;

public class InMemoryRepository {

    public static final Set<ProductEvent> inMemoryProductsInsert = new HashSet<>();
    public static final Set<ProductEventWithId> inMemoryProductsUpdate = new HashSet<>();
    public static final Set<ProductEventWithId> inMemoryProductsDelete = new HashSet<>();
}
