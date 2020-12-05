package com.hceris.crud.products;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "products")
@Getter
public class Product {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column()
    private LocalDate createdAt;
}
