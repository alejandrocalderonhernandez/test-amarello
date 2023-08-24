package com.alejandro.crud.controllers;

import com.alejandro.crud.entities.ProductDocument;
import com.alejandro.crud.entities.ProductRequest;
import com.alejandro.crud.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping(path = "product")
public class ProductController {

    private final ProductService productService;


    @GetMapping(path = "{id}")
    public ResponseEntity<ProductDocument> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> postProduct(@RequestBody ProductRequest product) {
        return ResponseEntity.created(URI.create(productService.save(product).getId())).build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ProductDocument> putProduct(@RequestBody ProductRequest product, @PathVariable String id) {
        return ResponseEntity.ok(productService.update(product, id));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
