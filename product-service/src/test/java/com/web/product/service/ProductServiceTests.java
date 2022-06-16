package com.web.product.service;

import com.web.product.dto.ProductDto;
import com.web.product.exception.InternalServerErrorException;
import com.web.product.exception.NotFoundException;
import com.web.product.model.Product;
import com.web.product.repository.ProductRepository;
import com.web.product.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Create Product Test")
    @Disabled
    public void createProductTest() {
        ProductDto productDto = generateProductDto();
        Product product = generateProduct();
        when(productRepository.findByProductCode(anyString())).thenReturn(product);
        assertThat(productService.createProduct(productDto)).isTrue();
    }

    @Test
    @DisplayName("Cannot Create Product")
    @Disabled
    public void cannotCreateProductTest() {
        ProductDto productDto = generateProductDto();
        productDto.setProductCode("");
        assertThat(productService.createProduct(productDto)).isFalse();
    }

    @Test
    @DisplayName("Get Product test")
    public void  getProductTest() {
        Product product = generateProduct();
        Mockito.when(productRepository.findByProductCode("test-123")).thenReturn(product);
        assertEquals("test-123", productService.getProduct(product.getProductCode()).getProductCode()); //  product.getProductCode()
    }

    @Test(expected = NotFoundException.class)
    @DisplayName("Get Product Not Found test")
    public void  getProductNotFoundTest() {
        when(productService.getProduct("")).thenThrow(new NotFoundException("Desired product not available."));
    }

    @Test
    @DisplayName("Check Product Exists Test")
    public void  checkProductExistsTest() {
        Product product = generateProduct();
        when(productRepository.findByProductCode("test-123")).thenReturn(product);
        assertEquals(true, productService.checkProductExists("test-123")); //  product.getProductCode()
    }

    @Test
    @DisplayName("Check Product Does Not Exists Test")
    public void  checkProductDoesNotExistsTest() {
        assertThat(productService.checkProductExists("")).isFalse();
    }


    private ProductDto generateProductDto() {
        ProductDto testProduct = new ProductDto();
        testProduct.setProductCode("test-123");
        testProduct.setProductName("Test Product");
        testProduct.setUnitPrice(10);
        return testProduct;
    }

    private Product generateProduct() {
        Product testProduct = new Product();
        testProduct.setProductCode("test-123");
        testProduct.setProductName("Test Product");
        testProduct.setUnitPrice(10);
        testProduct.setIsActive(true);
        testProduct.setImage("test.jpg");
        testProduct.setSkuCode("test-123");
        testProduct.setSellPrice(13);
        return testProduct;
    }

}
