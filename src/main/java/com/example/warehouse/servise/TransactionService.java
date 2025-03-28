package com.example.warehouse.servise;

import com.example.warehouse.db.ProductModel;
import com.example.warehouse.db.TransactionModel;
import com.example.warehouse.db.TransactionModelRepository;
import com.example.warehouse.enums.TransactionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionModelRepository transactionRepository;

    @Transactional
    public void crateTransaction(ProductModel productModel,TransactionType type, Integer quantity, Double price) {
        TransactionModel transaction = new TransactionModel();
        transaction.setType(type);
        transaction.setQuantity(quantity);
        transaction.setUnitPrice(price);
        transaction.setTotalAmount(quantity * price);
        transaction.setProduct(productModel);
        transactionRepository.save(transaction);
    }

    public void logAllTransactions() {
        transactionRepository.findAll()
                .forEach(transactionModel -> log.info(transactionModel.toString()));
    }
}
