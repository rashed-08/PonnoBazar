package com.web.inventory.service.impl;

import com.web.inventory.dto.StockDTO;
import com.web.inventory.service.StockService;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
    @Override
    public boolean CreateStock(StockDTO stockDTO) {
        return false;
    }
}
