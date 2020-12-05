package com.hceris.crud.products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test is more of a sanity check to ensure that the setup is correctly done.
 *
 * It doesn't really test any functionality implemented by us.
 */
@DataJpaTest
class ProductsRepositoryTest {

    @Autowired
    ProductsRepository repository;

    @Test
    void findReturnsEmptyOptionalIfDoesntExist() {
        Optional<Product> product = repository.findById(0L);
        assertThat(product).isEmpty();
    }

    @Test
    void findReturnsProduct() {
        Optional<Product> product = repository.findById(1L);
        assertThat(product).isNotEmpty();
        assertThat(product.get().getName()).isEqualTo("test_product");
    }
}
