package com.example.warehouse.servise;

import com.example.warehouse.DTOs.AddProductDTO;
import com.example.warehouse.DTOs.DiscountDTO;
import com.example.warehouse.DTOs.SellProductDTO;
import com.example.warehouse.db.ProductModel;
import com.example.warehouse.db.ProductModelRepository;
import com.example.warehouse.enums.TransactionType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductModelService {
    private final ProductModelRepository productRepository;
    private final TransactionService transactionService;

    @PostConstruct
    @Transactional
    public void init() {
        addProduct(new AddProductDTO("Яблоко",3,100.0));
        addProduct(new AddProductDTO("Яблоко",3));
        sellProduct(new SellProductDTO("Яблоко",3));
        setDiscount(new DiscountDTO("Яблоко", 10));
        addProduct(new AddProductDTO("Яблоко",3));
        sellProduct(new SellProductDTO("Яблоко",1));
        transactionService.logAllTransactions();
        countAllPrices();
    }

    @Transactional
    public void addProduct(AddProductDTO dto) {
        ProductModel product = productRepository.findByName(dto.getName())
                .orElse(new ProductModel(dto.getName(),0 , 0.0, 0));

        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (product.getQuantity() == 0)
            product.setQuantity(dto.getQuantity());
        else
            product.setQuantity(product.getQuantity() + dto.getQuantity());

        transactionService.crateTransaction(
                productRepository.save(product),
                TransactionType.ADD,
                dto.getQuantity(),
                product.getPrice() * (100 - product.getDiscount()) / 100
        );
    }

    @Transactional
    public ResponseEntity<Void> sellProduct(SellProductDTO dto) {
        ProductModel product = productRepository.findByName(dto.getName())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
                );

        int quantity = product.getQuantity() - dto.getQuantity();
        if (quantity >= 0)
            product.setQuantity(quantity);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity exceeds limit");

        transactionService.crateTransaction(
                productRepository.save(product),
                TransactionType.SELL,
                dto.getQuantity(),
                product.getPrice() * (100 - product.getDiscount()) / 100
        );

        return ResponseEntity.ok().build();
    }

    @Transactional
    public void setDiscount(DiscountDTO dto) {
        ProductModel product = productRepository.findByName(dto.getName())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
                );
        product.setDiscount(dto.getDiscount());
        productRepository.save(product);
    }

    public void countAllPrices() {
        log.info(String.valueOf(productRepository.countAll()));
    }


}
