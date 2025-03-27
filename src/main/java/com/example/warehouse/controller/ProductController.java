package com.example.warehouse.controller;

import com.example.warehouse.DTOs.SellProductDTO;
import com.example.warehouse.servise.ProductModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductModelService productModelService;

    @PostMapping("/sell")
    public ResponseEntity<Void> sellProduct(SellProductDTO sellProductDTO) {
        return productModelService.sellProduct(sellProductDTO);
    }
}
