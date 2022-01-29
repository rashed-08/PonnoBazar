package com.web.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.product.model.Product;
import com.web.product.repository.ProductRespository;
import com.web.product.service.ProductService;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<Product> getProducts(int page, int size) {
		if (page == 0) page = 1;
		if (size == 0) size = 10;
		Pageable paging = PageRequest.of(page, size);
		Page<Product> getPagedProducts = productRespository.findAll(paging);
		if (getPagedProducts.hasContent()) {
			return getPagedProducts.getContent();
		}
		return new ArrayList<Product>();
	}


}
