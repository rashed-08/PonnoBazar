package com.web.inventory.service.impl;

import com.web.inventory.model.Stock;
import com.web.inventory.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;
    private StockServiceImpl stockService;

    @BeforeEach
    void setUp() {
        stockService = new StockServiceImpl(stockRepository);
    }

    @Test
    void createStock() throws ParseException {
        //given
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

        //when
    }
}