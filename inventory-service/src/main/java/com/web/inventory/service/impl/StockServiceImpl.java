package com.web.inventory.service.impl;

import com.web.inventory.client.ProductServiceClient;
import com.web.inventory.dto.StockDTO;
import com.web.inventory.model.Stock;
import com.web.inventory.repository.StockRepository;
import com.web.inventory.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ProductServiceClient productServiceClient;

    public StockServiceImpl(StockRepository stockRepository, ProductServiceClient productServiceClient) {
        this.stockRepository = stockRepository;
        this.productServiceClient = productServiceClient;
    }

    @Override
    public boolean CreateStock(StockDTO stockDTO) {
        // check product available
        boolean checkProductExists = productServiceClient.checkProduct(stockDTO.getProductCode());
        if (checkProductExists) {
            // check if product stock already available
            Stock existedStock = stockRepository.findStockByProductCode(stockDTO.getProductCode());
            if (existedStock == null) {
                Stock newStock = new Stock();
                newStock.setProductCode(stockDTO.getProductCode());
                newStock.setCreatedDate(new Date());
                newStock.setQuantity(stockDTO.getQuantity());
                //save new stock
                stockRepository.save(newStock);
                // check saved successfully
                Stock savedStock = stockRepository.findStockByProductCode(stockDTO.getProductCode());
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
}
