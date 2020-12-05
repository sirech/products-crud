package com.hceris.crud.products.form;

import com.hceris.crud.products.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FormTest {

    Form form = new Form("product", BigDecimal.TEN);
    Product product = new Product(1L, "test_product", BigDecimal.valueOf(1), LocalDate.now(), false);

    @Test
    void asProductSetsCreatedAt() {
        assertThat(form.asProduct().getCreatedAt()).isNotNull();
    }

    @Test
    void asProductSetsDeleted() {
        assertThat(form.asProduct().getDeleted()).isFalse();
    }

    @Test
    void mergeDoesntChangeId() {
        assertThat(form.merge(product).getId()).isEqualTo(product.getId());
    }

    @Test
    void mergeDoesntCreatedAt() {
        assertThat(form.merge(product).getCreatedAt()).isEqualTo(product.getCreatedAt());
    }

    @Test
    void mergeChangesName() {
       assertThat(form.merge(product).getName()) .isEqualTo(form.getName());
    }

    @Test
    void mergeChangesPrice() {
        assertThat(form.merge(product).getPrice()) .isEqualTo(form.getPrice());
    }

    @Test
    void validateRejectsNullName() {
        assertThat(new Form(null, BigDecimal.TEN).validate()).isFalse();
    }

    @Test
    void validateRejectsEmptyName() {
        assertThat(new Form("", BigDecimal.TEN).validate()).isFalse();
    }

    @Test
    void validateRejectsEmptyPrice() {
        assertThat(new Form("product", null).validate()).isFalse();
    }

    @Test
    void validateRejectsNegativePrice() {
        assertThat(new Form("product", BigDecimal.valueOf(-1)).validate()).isFalse();
    }
}
