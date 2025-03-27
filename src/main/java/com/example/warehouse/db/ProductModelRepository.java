package com.example.warehouse.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductModelRepository extends JpaRepository<ProductModel, Long> {

    Optional<ProductModel> findByName(String name);

    @Query("select sum(p.price * p.quantity) from ProductModel p")
    long countAll();

}
