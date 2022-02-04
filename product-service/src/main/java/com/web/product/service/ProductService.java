package com.web.product.service;

import java.util.List;

import com.web.product.dto.ProductDto;
import com.web.product.model.Product;

public interface ProductService {

	boolean createProduct(ProductDto product);
	Product getProduct(String productCode);
	boolean checkProductExists(String productCode);
	List<Product> getProducts(int page, int size);
	boolean updateProduct(String productCode, Product product);
	boolean deleteProduct(String productCode);
}
