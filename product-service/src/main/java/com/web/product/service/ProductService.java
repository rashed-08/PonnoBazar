package com.web.product.service;

import com.web.product.dto.ProductDto;
import com.web.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

	boolean createProduct(ProductDto product);
	Product getProduct(String productCode);
	List<Product> getProducts(Pageable pageable);
	boolean updateProduct(String productCode, Product product);
	boolean deleteProduct(String productCode);
	boolean checkProductExists(String productCode);
}
