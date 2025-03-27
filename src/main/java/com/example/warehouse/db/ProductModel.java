package com.example.warehouse.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_model")
@NoArgsConstructor
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private Integer quantity;
    private Double price;
    private Integer discount;

    public ProductModel(
            String name,
            Integer quantity,
            Double price,
            Integer discount
    ) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }
}
