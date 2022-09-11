package com.web.product.service.impl;

import com.web.product.dto.ProductDto;
import com.web.product.exception.InternalServerErrorException;
import com.web.product.exception.NotFoundException;
import com.web.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	@CacheEvict(value = "product", allEntries = true)
	public boolean createProduct(ProductDto productDto) {
		ModelMapper modelMapper = new ModelMapper();
		logger.info("product_dto: {}", productDto.toString());
		if ((productDto.getProductCode() != null) && (!productDto.getProductCode().equals(""))) {
			Product product = modelMapper.map(productDto, Product.class);
			try {
				product.setIsActive(true);
				productRepository.save(product);
				Product saveProduct = getProduct(product.getProductCode());
				if (saveProduct.getProductCode().equals(product.getProductCode())) {
					logger.info("product_save");
					return true;
				}
			} catch (InternalServerErrorException e) {
				logger.error("product save internal server error");
				throw new InternalServerErrorException("Can't create product");
			}
		}
		logger.error("product code empty or null, could not save");
		return false;
	}

	@Override
	@Cacheable("product")
	public Product getProduct(String productCode) {
		logger.info("product_code: {}", productCode);
		if (productCode != null && !productCode.equals("")) {
			Product product = productRepository.findByProductCode(productCode);
			if (product != null && product.getIsActive()) {
				return product;
			}
			logger.error("product is null or not activated. could not fetch");
			throw new NotFoundException("Desired product not available.");
		}
		logger.error("product code empty or null, get product");
		throw new NotFoundException("Desired product not available.");
	}

	@Override
	public boolean checkProductExists(String productCode) {
		logger.info("product_code: {}", productCode);
		if (productCode != null && !productCode.equals("")) {
			Product product = getProduct(productCode);
			if (product != null) {
				return true;
			}
		}
		logger.error("product code empty or null, check product exist");
		return false;
	}

	@Override
	@Cacheable("product")
	public List<Product> getProducts(Pageable pageable) {
		Page<Product> getPagedProducts = productRepository.findAll(pageable);
		if (getPagedProducts != null && getPagedProducts.hasContent()) {
			List<Product> products = getPagedProducts.getContent()
					.stream().filter(x -> x.getIsActive())
					.collect(Collectors.toList());
			return products;
		}
		logger.error("Product list not available");
		throw new NotFoundException("Product doesn't exist.");
	}

	@Override
	@CachePut(value = "product", key = "#product.productCode")
	public boolean updateProduct(String productCode, Product product) {
		logger.info("product_code: {}, Product: {}", productCode, product);
		if (productCode != null && !productCode.equals("") && product != null) {
			Product existingProduct = getProduct(productCode);
			if (existingProduct != null) {
				if (!product.getProductCode().isEmpty()) {
					productRepository.save(product);
					return true;
				}
				logger.info("Empty product code, could not update");
			}
		}
		logger.error("product code empty or null and not empty product");
		throw new InternalServerErrorException("Product can't update.");
	}

	@Override
	@CacheEvict(value = "product", allEntries = true)
	public boolean deleteProduct(String productCode) {
		logger.info("product_code: {}", productCode);
		if (productCode != null && !productCode.equals("")) {
			Product existingProduct = getProduct(productCode);
			if (existingProduct != null) {
				existingProduct.setIsActive(false);
				productRepository.save(existingProduct);
				return true;
			}
			logger.error("product does not exist, could not delete");
		}
		logger.error("product code empty or null, could not delete");
		return false;
	}
}
