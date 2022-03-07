package com.web.inventory.controller;

import com.web.inventory.dto.StockDTO;
import com.web.inventory.model.Stock;
import com.web.inventory.service.impl.StockServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/inventory")
public class StockController {

    private final StockServiceImpl stockService;

    public StockController(StockServiceImpl stockService) {
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

    @GetMapping
    public ResponseEntity<List<Stock>> findAllStock() {
        List<Stock> allStock = stockService.getAllStock();
        if (allStock.size() > 0) {
            return ResponseEntity.ok(allStock);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<Boolean> updateStock(@RequestBody StockDTO stockDTO) {
        String productCode = stockDTO.getProductCode();
        int quantity = stockDTO.getQuantity();
        boolean updateStock = stockService.updateStock(productCode, quantity);
        if (updateStock) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @GetMapping(path = "/{product_code}")
    public ResponseEntity<Stock> getStock(@PathVariable("product_code") String productCode) {
        Stock currentStock = stockService.getStock(productCode);
        if (currentStock != null) {
            return ResponseEntity.ok(currentStock);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/available/{product_code}/{quantity}")
    public ResponseEntity<Boolean> isStockAvailable(@PathVariable("product_code") String productCode,@PathVariable("quantity") Integer quantity) {
        boolean isStockAvailable = stockService.isStockAvailable(productCode, quantity);
        if (isStockAvailable) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }

    @DeleteMapping(path = "/{product_code}")
    public ResponseEntity<Boolean> deleteStock(@PathVariable("product_code") String productCode) {
        boolean deleteStock = stockService.deleteStock(productCode);
        if (deleteStock) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);

    }
}
