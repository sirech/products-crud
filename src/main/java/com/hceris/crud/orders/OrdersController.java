package com.hceris.crud.orders;

import io.swagger.annotations.ApiOperation;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

@RestController
@RequestMapping(value = "/rest/users/{email}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdersController {
    private OrdersRepository repository;

    public OrdersController(@Autowired OrdersRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    @ApiOperation("Gets all orders")
    public ResponseEntity<List<Order>> orders(@PathVariable String email) {
        return ResponseEntity.ok(List.ofAll(repository.findAllByEmail(UriUtils.decode(email, "UTF-8"))));
    }
}
