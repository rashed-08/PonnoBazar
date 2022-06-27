package com.web.inventory.service.impl;

import com.web.inventory.client.ProductServiceClient;
import com.web.inventory.dto.StockDTO;
import com.web.inventory.exception.InternalServerErrorExceptionHandler;
import com.web.inventory.exception.NotFoundException;
import com.web.inventory.model.Stock;
import com.web.inventory.repository.StockRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private ProductServiceClient productServiceClient;

    @Test
    @DisplayName("Create Stock Test")
    void createStockTest() {
        StockDTO stockDTO = prepareStockDto();
        Stock stock = prepareStock();
        when(productServiceClient.checkProduct(anyString())).thenReturn(true);
        when(stockRepository.findStockByProductCode(anyString())).thenReturn(null).thenReturn(stock);
        assertThat(stockService.createStock(stockDTO)).isTrue();
    }

    @Test
    @DisplayName("Create Stock without Product Exception Test")
    public void createStockWithoutProductExceptionTest() {
        StockDTO stockDTO = prepareStockDto();
        when(productServiceClient.checkProduct(anyString())).thenReturn(false);
        assertThrows(InternalServerErrorExceptionHandler.class, () -> stockService.createStock(stockDTO), "Can't fetch product.");
    }

    @Test
    @DisplayName("Already Added Stock Exception Test")
    public void alreadyAddedStockExceptionTest() {
        StockDTO stockDTO = prepareStockDto();
        Stock stock = prepareStock();
        when(productServiceClient.checkProduct(anyString())).thenReturn(true);
        when(stockRepository.findStockByProductCode(anyString())).thenReturn(stock);
        assertThrows(InternalServerErrorExceptionHandler.class, () -> stockService.createStock(stockDTO), "Stock already added. Please update the stock.");
    }


    @Test
    @DisplayName("Get All Stock List Test")
    void getStockListTest() {
        List<Stock> allStock = prepareStockList();
        when(stockRepository.findAll()).thenReturn(allStock);
        assertThat(stockService.getAllStock().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Empty Stock List Test")
    public void emptyStockListTest() {
        assertThrows(NotFoundException.class, () -> stockService.getAllStock(), "Stock list not found.");
    }

    @Test
    @DisplayName("Get Stock Test")
    void getStockTest() {
        Stock stock = prepareStock();
        when(stockRepository.findStockByProductCode(anyString())).thenReturn(stock);
        assertThat(stockService.getStock(anyString()).getProductCode()).isEqualTo("test-001");
        assertThat(stockService.getStock(anyString()).getQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("Get Empty Stock Test")
    public void getEmptyStockTest() {
        assertThrows(NotFoundException.class, () -> stockService.getStock(anyString()), "Could not found stock.");
    }

    @Test
    @DisplayName("Get Product Code Empty Test")
    public void getProductCodeEmptyTest() {
        assertThrows(InternalServerErrorExceptionHandler.class, () -> stockService.getStock(""), "Product code can't be empty.");
    }


    @Test
    @DisplayName("Get Stock Available Test")
    void getAvailableStockTest() {
        Stock stock = prepareStock();
        when(stockRepository.findStockByProductCode(anyString())).thenReturn(stock);
        assertThat(stockService.isStockAvailable(anyString(), 4)).isTrue();
    }

    @Test
    @DisplayName("Check Available Stock Test")
    void checkAvailableStockTest() {
        Stock stock = prepareEmptyStock();
        Stock maxStock = prepareStock();
        maxStock.setQuantity(4);
        when(stockRepository.findStockByProductCode(anyString())).thenReturn(stock).thenReturn(maxStock);
        assertThat(stockService.isStockAvailable(stock.getProductCode(), 4)).isFalse();
        assertThat(stockService.isStockAvailable(maxStock.getProductCode(), 39)).isFalse();
    }

    @Test
    @DisplayName("Update Stock Test")
    void updateStockTest() {
        StockDTO stockDTO = prepareUpdateStockDto();
        Stock stock = prepareStock();
        when(productServiceClient.checkProduct(anyString())).thenReturn(true);
        when(stockRepository.findStockByProductCode(anyString())).thenReturn(stock).thenReturn(stock);
        assertThat(stockService.updateStock(stockDTO)).isTrue();
    }

    @Test
    @DisplayName("Update Stock with Exception Test")
    public void updateStockWithExceptionTest() {
        StockDTO stockDTO = prepareStockDto();
        when(productServiceClient.checkProduct(anyString())).thenReturn(false);
        assertThrows(InternalServerErrorExceptionHandler.class, () -> stockService.updateStock(stockDTO), "Can't fetch product.");
    }

    @Test
    @DisplayName("Update Stock without Product Exception Test")
    public void updateStockWithoutProductExceptionTest() {
        StockDTO stockDTO = prepareStockDto();
        when(productServiceClient.checkProduct(anyString())).thenReturn(false);
        assertThrows(InternalServerErrorExceptionHandler.class, () -> stockService.updateStock(stockDTO), "Can't fetch product.");
    }

    @Test
    @DisplayName("Update Null Stock Exception Test")
    public void updateNullStockExceptionTest() {
        StockDTO stockDTO = prepareStockDto();
        when(productServiceClient.checkProduct(anyString())).thenReturn(true);
        when(stockRepository.findStockByProductCode(stockDTO.getProductCode())).thenReturn(null);
        assertThrows(NotFoundException.class, () -> stockService.updateStock(stockDTO), "Could not found stock.");
    }

    @Test
    @DisplayName("Cannot Update Stock Test")
    void cannotUpdateStockTest() {
        StockDTO stockDTO = prepareUpdateStockDto();
        Stock stock = prepareStock();
        when(productServiceClient.checkProduct(anyString())).thenReturn(true);
        when(stockRepository.findStockByProductCode(anyString())).thenReturn(stock).thenReturn(null);
        assertThrows(NotFoundException.class, () -> stockService.updateStock(stockDTO), "Could not found stock.");
    }

    @Test
    @DisplayName("Delete Stock Test")
    void deleteStockTest() {
        Stock stock = prepareStock();
        when(stockRepository.findStockByProductCode(anyString())).thenReturn(stock);
        assertThat(stockService.deleteStock(stock.getProductCode())).isTrue();
    }


    private StockDTO prepareStockDto() {
        return new StockDTO(
                "test-001",
                10
        );
    }

    private Stock prepareStock() {
        Stock stock = new Stock();
        stock.setProductCode("test-001");
        stock.setCreatedDate(new Date());
        stock.setQuantity(10);
        return stock;
    }

    private StockDTO prepareUpdateStockDto() {
        StockDTO stockDto = new StockDTO();
        stockDto.setProductCode("test-001");
        stockDto.setQuantity(15);
        return stockDto;
    }


    private Stock prepareEmptyStock() {
        Stock stock = new Stock();
        stock.setProductCode("test-001");
        stock.setCreatedDate(new Date());
        stock.setQuantity(0);
        return stock;
    }

    private List<Stock> prepareStockList() {
        return List.of(
                new Stock(1, "test-001", new Date(), null, 10),
                new Stock(2, "test-002", new Date(), null, 12),
                new Stock(3, "test-003", new Date(), null, 15),
                new Stock(4, "test-004", new Date(), null, 4)
        );
    }
}