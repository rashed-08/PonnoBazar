package com.web.inventory.repository;

import com.web.inventory.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {
    Stock findStockByProductCode(String productCode);
}
