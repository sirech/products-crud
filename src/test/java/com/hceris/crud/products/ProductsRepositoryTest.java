package com.hceris.crud.products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test is more of a sanity check to ensure that the setup is correctly done.
 * <p>
 * It doesn't really test any functionality implemented by us.
 */
@DataJpaTest
class ProductsRepositoryTest {
    @Autowired
    ProductsRepository repository;

    Product product = new Product(null, "new_product", BigDecimal.valueOf(1), LocalDate.now(), false);

    @Test
    void findReturnsEmptyIfDoesntExist() {
        Optional<Product> product = repository.findById(0L);
        assertThat(product).isEmpty();
    }

    @Test
    void findReturnsEmptyIfProductDeleted() {
        Optional<Product> product = repository.findById(3L);
        assertThat(product).isEmpty();
    }

    @Test
    void findReturnsProduct() {
        Optional<Product> product = repository.findById(1L);
        assertThat(product).isNotEmpty();
        assertThat(product.get().getName()).isEqualTo("test_product");
    }

    @Test
    void findAllExcludesDeletedItems() {
        Iterable<Product> products = repository.findAll();
        assertThat(products).allSatisfy(product -> {
            assertThat(product.getDeleted()).isFalse();
        });
    }

    @Test
    void findAllReturnsAList() {
        Iterable<Product> products = repository.findAll();
        assertThat(products).isNotEmpty();
    }

    @Test
    void softDeleteSetsDeletedFlag() {
        assertThat(repository.findById(1L)).isNotEmpty();
        repository.softDelete(1L);
        assertThat(repository.findById(1L)).isEmpty();
    }

    @Test
    void createInsertsANewProduct() {
        long count = repository.count();
        repository.save(product);
        assertThat(repository.count()).isEqualTo(count + 1);
    }
}
