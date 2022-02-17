package com.web.inventory.service.impl;

import com.web.inventory.dto.StockDTO;
import com.web.inventory.model.Stock;
import com.web.inventory.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

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

        // given
        StockDTO stockDTO = new StockDTO("test-001", 20);

        // when
        stockService.createStock(stockDTO);

        // then
        ArgumentCaptor<Stock> stockArgumentCaptor = ArgumentCaptor.forClass(Stock.class);
        verify(stockRepository).save(stockArgumentCaptor.capture());

        Stock capturedStock = stockArgumentCaptor.getValue();
        assertThat(capturedStock.getProductCode()).isEqualTo(stockDTO.getProductCode());
    }
}