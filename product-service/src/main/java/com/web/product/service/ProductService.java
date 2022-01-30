package com.web.product.service;

import com.web.product.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

	boolean createProduct(Product product);
	Product getProduct(String productCode);
	List<Product> getProducts(int page, int size);
	boolean updateProduct(String productCode, Product product);
	boolean deleteProduct(String productCode);
}
