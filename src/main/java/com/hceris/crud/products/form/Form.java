package com.hceris.crud.products.form;

import com.hceris.crud.products.Product;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class Form {
    String name;
    BigDecimal price;

    public Product asProduct() {
        return Product
                .builder()
                .name(name)
                .price(price)
                .createdAt(LocalDate.now())
                .deleted(false)
                .build();
    }

    public Product merge(Product original) {
        return Product
                .builder()
                .id(original.getId())
                .createdAt(original.getCreatedAt())
                .name(name)
                .price(price)
                .deleted(original.getDeleted())
                .build();
    }

    public boolean validate() {
        if (name == null || name.isBlank()) {
            return false;
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        return true;
    }
}
