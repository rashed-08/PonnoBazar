package com.web.inventory.service.impl;

import com.web.inventory.client.ProductServiceClient;
import com.web.inventory.dto.StockDTO;
import com.web.inventory.model.Stock;
import com.web.inventory.repository.StockRepository;
import com.web.inventory.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private StockRepository stockRepository;
    private ProductServiceClient productServiceClient;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository, ProductServiceClient productServiceClient) {
        this.stockRepository = stockRepository;
        this.productServiceClient = productServiceClient;
    }

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public boolean createStock(StockDTO stockDTO) {
        // check product available
        boolean checkProductExists = productServiceClient.checkProduct(stockDTO.getProductCode());
        if (checkProductExists) {
            // check if product stock already available
            Stock existedStock = getStock(stockDTO.getProductCode());
            if (existedStock == null) {
                Stock newStock = new Stock();
                newStock.setProductCode(stockDTO.getProductCode());
                newStock.setCreatedDate(new Date());
                newStock.setQuantity(stockDTO.getQuantity());
                //save new stock
                stockRepository.save(newStock);
                // check saved successfully
                Stock savedStock = getStock(stockDTO.getProductCode());
                if (savedStock.getProductCode().equals(stockDTO.getProductCode())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public List<Stock> getAllStock() {
        List<Stock> getAllStock = stockRepository.findAll();
        if (getAllStock.size() > 0) {
            return getAllStock;
        }
        return null;
    }

    @Override
    public Stock getStock(String productCode) {
        Stock stock = stockRepository.findStockByProductCode(productCode);
        if (stock != null) {
            return stock;
        }
        return null;
    }

    @Override
    public boolean isStockAvailable(String productCode, Integer quantity) {
        Stock stock = getStock(productCode);
        if (stock.getQuantity() > 0 && quantity< stock.getQuantity()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStock(String productCode, Integer quantity) {
        Stock stock = getStock(productCode);
        if (stock != null) {
            boolean isStockAvailable = isStockAvailable(productCode, quantity);
            if (isStockAvailable) {
                stock.setProductCode(productCode);
                stock.setQuantity(quantity);
                stock.setUpdatedDate(new Date());
                stockRepository.save(stock);
                Stock updatedStock = getStock(productCode);
                if (updatedStock.getProductCode().equals(productCode)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteStock(String productCode) {
        Stock stock = getStock(productCode);
        if (stock != null) {
            stockRepository.delete(stock);
            Stock deletedStock = getStock(productCode);
            if (deletedStock == null) {
                return true;
            }
            return false;
        }
        return false;
    }

}
