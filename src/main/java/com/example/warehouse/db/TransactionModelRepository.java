package com.example.warehouse.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionModelRepository extends JpaRepository<TransactionModel, Long> {
}
