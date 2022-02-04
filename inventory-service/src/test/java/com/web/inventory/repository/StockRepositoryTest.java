package com.web.inventory.repository;

import com.web.inventory.model.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;



    @Test
    public void checkStockWithSavedProductCode() throws ParseException {
        // given
        String today = "02-03-22";
        String productCode = "test-001";
        Date date = new SimpleDateFormat("MM-dd-yy").parse(today);
        Stock stock = new Stock(
                100,
                productCode,
                date,
                date,
                10
        );
        stockRepository.save(stock);

        //when
        Stock savedStock = stockRepository.findStockByProductCode(productCode);

        //then
        assertThat(savedStock.getProductCode()).isEqualTo(productCode);
        assertThat(savedStock.getId()).isEqualTo(stock.getId());
    }

    @Test
    public void checkStockWithoutSavedProductCode() throws ParseException {
        // given
        String today = "02-03-22";
        String productCode = "test-001";
        Date date = new SimpleDateFormat("MM-dd-yy").parse(today);
        Stock stock = new Stock(
                100,
                productCode,
                date,
                date,
                10
        );
        stockRepository.save(stock);

        //when
        Stock savedStock = stockRepository.findStockByProductCode(productCode);

        //then
        assertThat(savedStock.getProductCode()).isNotEqualTo("test-002");
    }
}