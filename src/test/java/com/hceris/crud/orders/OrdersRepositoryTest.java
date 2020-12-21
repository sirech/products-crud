package com.hceris.crud.orders;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrdersRepositoryTest {
    @Autowired
    OrdersRepository repository;

    @Test
    void findAllByEmailReturnsEmptyListIfNoResults() {
        Iterable<Order> orders = repository.findAllByEmail("nope@gmail.com");
        assertThat(orders).isEmpty();
    }

    @Test
    void findAllByEmailReturnsListOfOrders() {
        Iterable<Order> orders = repository.findAllByEmail("consumer@gmail.com");
        assertThat(orders).isNotEmpty();
    }

    @Test
    void createInsertsANewOrder() {
        long count = repository.count();
        repository.save(new Order.OrderBuilder()
                .email("test@gmail.com")
                .build()
        );
        assertThat(repository.count()).isEqualTo(count + 1);
    }
}
