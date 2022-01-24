package com.web.product.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.product.dto.ProductDto;
import com.web.product.model.Product;
import com.web.product.service.ProductService;

@RestController
@RequestMapping(path = "api/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	public ResponseEntity<HttpStatus> createProduct(@RequestBody ProductDto productDto) {
		Product product = modelMapper.map(productDto, Product.class);
		if (productService.createProduct(product)) {
			return ResponseEntity.ok(HttpStatus.CREATED);
		}
		return ResponseEntity.ok(HttpStatus.BAD_GATEWAY);
	}
	
	@GetMapping("/{product_code}")
	public ResponseEntity<Product> getProduct(@PathVariable("product_code") String productCode) {
		Product getProduct = productService.getProduct(productCode);
		if (getProduct != null) {
			return ResponseEntity.ok(getProduct);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
