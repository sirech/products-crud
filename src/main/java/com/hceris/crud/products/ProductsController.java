package com.hceris.crud.products;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/rest/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsController {
    private ProductsRepository repository;

    public ProductsController(@Autowired ProductsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("{id}")
    @ApiOperation("Gets a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Product with given id doesn't exist"),
    })
    public ResponseEntity<Product> product(
            @ApiParam(value = "id of the product", required = true)
            @PathVariable Long id) {
        Optional<Product> product = repository.findById(id);

        return product
                .map(p -> ResponseEntity.ok(p))
                .orElse(ResponseEntity.status(404).build());
    }

    @GetMapping("")
    @ApiOperation("Gets all products")
    public ResponseEntity<List<Product>> products() {
        List<Product> products = StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }


}
