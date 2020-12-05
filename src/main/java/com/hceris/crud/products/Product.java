package com.hceris.crud.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "products")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column(name = "created_at")
    @JsonProperty("created_at")
    private LocalDate createdAt;

    @Column
    @JsonIgnore
    private Boolean deleted;
}
