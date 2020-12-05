package com.hceris.crud.products.form;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CreateFormTest {

    CreateForm form = new CreateForm("product", BigDecimal.TEN);

    @Test
    void asProductSetsCreatedAt() {
        assertThat(form.asProduct().getCreatedAt()).isNotNull();
    }

    @Test
    void asProductSetsDeleted() {
        assertThat(form.asProduct().getDeleted()).isFalse();
    }
}
