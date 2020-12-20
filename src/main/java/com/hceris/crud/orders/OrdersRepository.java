package com.hceris.crud.orders;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface OrdersRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findAllByEmail(@NonNull String email);
}
