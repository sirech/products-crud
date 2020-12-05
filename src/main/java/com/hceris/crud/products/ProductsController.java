package com.hceris.crud.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/rest/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsController {
    private ProductsRepository repository;

    public ProductsController(@Autowired ProductsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> product(@PathVariable Long id) {
        Optional<Product> product = repository.findById(id);

        return product
                .map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.status(404).build());
    }
}
