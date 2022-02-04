package com.web.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.product.dto.ProductDto;
import com.web.product.model.Product;
import com.web.product.repository.ProductRepository;
import com.web.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;
	private final ModelMapper modelMapper;

	public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public boolean createProduct(ProductDto productDto) {
		Product product = modelMapper.map(productDto, Product.class);
		Product existedProduct = getProduct(product.getProductCode());
		if (existedProduct == null) {
			product.setIsActive(true);
			productRepository.save(product);
			Product saveProduct = getProduct(product.getProductCode());
			if (saveProduct.getProductCode().equals(product.getProductCode())) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public Product getProduct(String productCode) {
		Product getProduct = productRepository.findByProductCode(productCode);
		if (getProduct != null) {
			return getProduct;
		}
		return null;
	}

	@Override
	public boolean checkProductExists(String productCode) {
		Product product = getProduct(productCode);
		if (product != null && product.getIsActive()) {
			return true;
		}
		return false;
	}

	@Override
	public List<Product> getProducts(int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		Page<Product> getPagedProducts = productRepository.findAll(paging);
		if (getPagedProducts.hasContent()) {
			List<Product> products = getPagedProducts.getContent()
										.stream().filter(x -> x.getIsActive())
										.collect(Collectors.toList());
			return products;
		}
		return new ArrayList<Product>();
	}

	@Override
	public boolean updateProduct(String productCode, Product product) {
		Product existingProduct = getProduct(productCode);
		if (existingProduct != null) {
			if (!product.getProductCode().isEmpty()) {
				productRepository.save(product);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteProduct(String productCode) {
		Product existingProduct = getProduct(productCode);
		if (existingProduct != null) {
			existingProduct.setIsActive(false);
			productRepository.save(existingProduct);
			return true;
		}
		return false;
	}
}
