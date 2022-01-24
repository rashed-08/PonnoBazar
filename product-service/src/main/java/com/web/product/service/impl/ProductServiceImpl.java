package com.web.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.product.model.Product;
import com.web.product.repository.ProductRespository;
import com.web.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRespository productRespository;

	@Override
	public boolean createProduct(Product product) {
		productRespository.save(product);
		Product saveProduct = getProduct(product.getProductCode());
		if (saveProduct.getProductCode().equals(product.getProductCode())) {
			return true;
		}
		return false;
	}

	@Override
	public Product getProduct(String productCode) {
		Product getProduct = productRespository.findByProductCode(productCode);
		if (getProduct != null) {
			return getProduct;
		}
		return null;
	}

}
