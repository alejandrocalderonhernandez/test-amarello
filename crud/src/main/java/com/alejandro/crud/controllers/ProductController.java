package com.alejandro.crud.controllers;

import com.alejandro.crud.entities.ProductDocument;
import com.alejandro.crud.entities.ProductRequest;
import com.alejandro.crud.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDocument> postProduct(@RequestBody ProductRequest product) {
        return ResponseEntity.ok(this.productService.save(product));
    }
}
