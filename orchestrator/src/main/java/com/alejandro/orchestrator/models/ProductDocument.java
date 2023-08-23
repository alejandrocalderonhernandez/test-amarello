package com.alejandro.orchestrator.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class ProductDocument {

    @Id
    private String id;
    private String name;
}
