package com.web.inventory.service;

import com.web.inventory.dto.StockDTO;

public interface StockService {
    boolean CreateStock(StockDTO stockDTO);
}
