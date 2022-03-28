package com.web.product.service.impl;

import com.web.product.dto.ProductDto;
import com.web.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web.product.model.Product;
import com.web.product.repository.ProductRepository;
import com.web.product.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	@CacheEvict(value = "product", allEntries = true)
	public boolean createProduct(ProductDto productDto) {
		ModelMapper modelMapper = new ModelMapper();
		Product product = modelMapper.map(productDto, Product.class);
		try {
			product.setIsActive(true);
			productRepository.save(product);
			Product saveProduct = getProduct(product.getProductCode());
			if (saveProduct.getProductCode().equals(product.getProductCode())) {
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException("Can't create product");
		}
		return false;
	}

	@Override
	@Cacheable("product")
	public Product getProduct(String productCode) {
		try {
			Product product = productRepository.findByProductCode(productCode);
			if (product != null && product.getIsActive()) {
				return product;
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Cannot find product!");
		} catch (RuntimeException e) {
			throw new RuntimeException("Internal server error!");
		}
		return null;
	}

	@Override
	public boolean checkProductExists(String productCode) {
		try {
			Product product = getProduct(productCode);
			if (product != null) {
				return true;
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Cannot find product!");
		} catch (RuntimeException e) {
			throw new RuntimeException("Internal server error!");
		}
		return false;
	}

	@Override
	@Cacheable("product")
	public List<Product> getProducts(Pageable pageable) {
		Page<Product> getPagedProducts = productRepository.findAll(pageable);
		try {
			if (getPagedProducts.hasContent()) {
				List<Product> products = getPagedProducts.getContent()
						.stream().filter(x -> x.getIsActive())
						.collect(Collectors.toList());
				return products;
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Cannot fetch product list");
		}
		return null;
	}

	@Override
	@CachePut(value = "product", key = "#product.productCode")
	public boolean updateProduct(String productCode, Product product) {
		try {
			Product existingProduct = getProduct(productCode);
			if (existingProduct != null) {
				if (!product.getProductCode().isEmpty()) {
					productRepository.save(product);
					return true;
				}
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Cannot update product!");
		} catch (RuntimeException e) {
			throw new RuntimeException("Internal server error!");
		}
		return false;
	}

	@Override
	@CacheEvict(value = "product", allEntries = true)
	public boolean deleteProduct(String productCode) {
		try {
			Product existingProduct = getProduct(productCode);
			if (existingProduct != null) {
				existingProduct.setIsActive(false);
				productRepository.save(existingProduct);
				return true;
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Cannot delete product!");
		} catch (RuntimeException e) {
			throw new RuntimeException("Internal server error!");
		}
		return false;
	}
}
