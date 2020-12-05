package com.hceris.crud.products.form;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class FormTest {

    Form form = new Form("product", BigDecimal.TEN);

    @Test
    void asProductSetsCreatedAt() {
        assertThat(form.asProduct().getCreatedAt()).isNotNull();
    }

    @Test
    void asProductSetsDeleted() {
        assertThat(form.asProduct().getDeleted()).isFalse();
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
