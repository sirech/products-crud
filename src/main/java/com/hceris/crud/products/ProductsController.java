package com.hceris.crud.products;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsController {

    @GetMapping("{id}")
    public ResponseEntity<Product> product(@PathVariable Long id) {
        return ResponseEntity.status(404).build();
    }
}
