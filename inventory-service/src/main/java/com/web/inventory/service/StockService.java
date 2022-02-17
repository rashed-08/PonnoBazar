package com.web.inventory.service;

import com.web.inventory.dto.StockDTO;
import com.web.inventory.model.Stock;

public interface StockService {
    boolean createStock(StockDTO stockDTO);
    Stock getStock(String productCode);
}
