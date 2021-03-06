package com.hceris.crud.products;

import com.hceris.crud.products.form.Form;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    @ApiOperation("Gets all products")
    public ResponseEntity<List<Product>> products() {
        List<Product> products = List.ofAll(repository.findAll());
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Deletes a product. The product won't be removed from the database", code = 204, response = Void.class)
    @ApiResponse(code = 204, message = "Success")
    public ResponseEntity<Product> softDelete(
            @ApiParam(value = "id of the product", required = true)
            @PathVariable Long id) {
        repository.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Creates a new product and returns its id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success"),
            @ApiResponse(code = 400, message = "Provided values are incorrect"),
    })
    public ResponseEntity<Long> create(@RequestBody Form form) {
        if (!form.validate()) {
            return ResponseEntity.badRequest().build();
        }

        Product product = repository.save(form.asProduct());
        return ResponseEntity.status(201).body(product.getId());
    }

    // It's a bit debatable whether this should be a PUT or a POST.
    // The updated_at will get changed, so it doesn't quite fulfill the PUT semantics 100%
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Updates an existing product")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success"),
            @ApiResponse(code = 400, message = "Provided values are incorrect"),
            @ApiResponse(code = 404, message = "Product with given id doesn't exist"),
    })
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Form form) {
        if (!form.validate()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        product.ifPresent(p -> repository.save(form.merge(p)));
        return ResponseEntity.noContent().build();
    }

    /**
     * This is a blanket catch-all exception to avoid showing 500s to the client
     * <p>
     * It's there only for prevention reasons. Exceptions should be handled in the right layers.
     * Should cover things like DB being down or others where there is no concrete handling plan.
     * </p>
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception e) {
        return ResponseEntity.status(502).build();
    }
}
