package com.hceris.crud.orders.form;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hceris.crud.orders.Order;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Form {
    @JsonProperty
    String email;

    public Order asOrder() {
        return Order
                .builder()
                .email(email)
                .build();
    }

    public boolean validate() {
        if (email == null || email.isBlank()) {
            return false;
        }

        return true;
    }
}
