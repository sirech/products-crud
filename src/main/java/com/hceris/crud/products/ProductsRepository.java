package com.hceris.crud.products;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductsRepository extends CrudRepository<Product, Long> {
    @Override
    @Query("select e from #{#entityName} e where e.id = ?1 and e.deleted = false")
    Optional<Product> findById(Long aLong);

    @Override
    @Query("select e from #{#entityName} e where e.deleted = false")
    Iterable<Product> findAll();
}
