package com.web.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.web.product.model.Product;

@Repository
public interface ProductRespository extends MongoRepository<Product, Integer> {

	Product findByProductCode(String productCode);
}
