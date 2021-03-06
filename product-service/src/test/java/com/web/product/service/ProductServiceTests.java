package com.web.product.service;

import com.web.product.dto.ProductDto;
import com.web.product.exception.InternalServerErrorException;
import com.web.product.exception.NotFoundException;
import com.web.product.model.Product;
import com.web.product.repository.ProductRepository;
import com.web.product.service.impl.ProductServiceImpl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Create Product Test")
    public void createProductTest() {
        ProductDto productDto = generateProductDto();
        Product product = generateProduct();
        when(productRepository.findByProductCode(anyString())).thenReturn(product);
        assertThat(productService.createProduct(productDto)).isTrue();
    }

    @Test
    @DisplayName("Cannot Create Product")
    public void cannotCreateProductTest() {
        ProductDto productDto = generateProductDto();
        productDto.setProductCode("");
        assertThat(productService.createProduct(productDto)).isFalse();
    }

    @Test
    @DisplayName("Get Product test")
    public void  getProductTest() {
        Product product = generateProduct();
        Mockito.when(productRepository.findByProductCode(anyString())).thenReturn(product);
        assertEquals("test-123", productService.getProduct(product.getProductCode()).getProductCode()); //  product.getProductCode()
    }

    @Test
    @DisplayName("Get Product Not Found test")
    public void  getProductNotFoundTest() {
        assertThrows(NotFoundException.class, () -> productService.getProduct(""), "Desired product not available.");
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

    @Test
    @DisplayName("Get All Product Test")
    public void  getAllProductTest() {
        List<Product> allProducts = generateProductList();
        Pageable page = PageRequest.of(1, 4);
        when(productRepository.findAll(page)).thenReturn(new PageImpl<>(allProducts));
        assertEquals(3, productService.getProducts(page).size());
    }

    @Test
    @DisplayName("Get All Product Not Found Test")
    public void  getAllProductNotFoundTest() {
        Pageable page = PageRequest.of(1, 4);
        assertThrows(NotFoundException.class, () -> productService.getProducts(page), "Product doesn't exist.");
    }

    @Test
    @DisplayName("Update Product Test")
    public void updateProductTest() {
        Product product = generateProduct();
        when(productRepository.findByProductCode(anyString())).thenReturn(product);
        product.setProductName("Test Product - 1");
        assertThat(productService.updateProduct(product.getProductCode(), product)).isTrue();
        assertEquals("Test Product - 1", product.getProductName());
    }

    @Test
    @DisplayName("Cannot Update Product Test")
    public void cannotUpdateProductTest() {
        Product product = generateProduct();
        assertThrows(InternalServerErrorException.class, () -> productService.updateProduct(null, product),"Product can't update.");
        assertThrows(InternalServerErrorException.class, () -> productService.updateProduct("", product),"Product can't update.");
        assertThrows(InternalServerErrorException.class, () -> productService.updateProduct(product.getProductCode(), null), "Product can't update.");
    }

    @Test
    @DisplayName("Delete Product Test")
    public void deleteProductTest() {
        Product product = generateProduct();
        when(productRepository.findByProductCode(anyString())).thenReturn(product);
        assertThat(productService.deleteProduct(product.getProductCode())).isTrue();
        assertEquals(false, product.getIsActive());
    }

    @Test
    @DisplayName("Cannot Delete Product Test")
    public void cannotDeleteProductTest() {
        assertThat(productService.deleteProduct("")).isFalse();
        assertThat(productService.deleteProduct(null)).isFalse();
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

    private List<Product> generateProductList() {
        return List.of(
                new Product("test-001", "Test 1", "sku-1", 10, 12, "image1.jgp", true),
                new Product("test-002", "Test 2", "sku-2", 7, 10, "image2.jgp", true),
                new Product("test-003", "Test 3", "sku-3", 23, 27, "image3.jgp", true),
                new Product("test-004", "Test 4", "sku-4", 2, 12, "image4.jgp", false)
        );
    }

}
