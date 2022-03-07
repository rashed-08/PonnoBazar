package com.web.inventory.service;

import com.web.inventory.dto.StockDTO;
import com.web.inventory.model.Stock;

import java.util.List;

public interface StockService {
    boolean createStock(StockDTO stockDTO);
    List<Stock> getAllStock();
    Stock getStock(String productCode);
    boolean isStockAvailable(String productCode, Integer quantity);
    boolean updateStock(String productCode, Integer quantity);
    boolean deleteStock(String productCode);
}
