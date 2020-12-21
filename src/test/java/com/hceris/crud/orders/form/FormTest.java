package com.hceris.crud.orders.form;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormTest {
    @Test
    void validateRejectsNullName() {
        assertThat(new Form(null).validate()).isFalse();
    }

    @Test
    void validateRejectsEmptyName() {
        assertThat(new Form("").validate()).isFalse();
    }

}
