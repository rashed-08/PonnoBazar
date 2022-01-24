package com.web.product.service;

import com.web.product.model.Product;

public interface ProductService {

	boolean createProduct(Product product);
	Product getProduct(String productCode);
}
