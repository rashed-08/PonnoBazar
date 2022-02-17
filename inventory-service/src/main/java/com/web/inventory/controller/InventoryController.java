package com.web.inventory.controller;

import com.web.inventory.dto.StockDTO;
import com.web.inventory.service.impl.StockServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/inventory")
public class InventoryController {

    private final StockServiceImpl stockService;

    public InventoryController(StockServiceImpl stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createStock(@RequestBody StockDTO stockDTO)
    {
        boolean created = stockService.createStock(stockDTO);
        if (created) {
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }
}
