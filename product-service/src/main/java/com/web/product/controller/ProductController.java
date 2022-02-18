package com.web.product.controller;

import com.web.product.service.impl.ProductServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web.product.dto.ProductDto;
import com.web.product.model.Product;

import java.util.List;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
	
	private final ProductServiceImpl productService;
	private final ModelMapper modelMapper;

	@Autowired
	public ProductController(ProductServiceImpl productService, ModelMapper modelMapper) {
		this.productService = productService;
		this.modelMapper = modelMapper;
	}

	@PostMapping
	public ResponseEntity<HttpStatus> createProduct(@RequestBody ProductDto productDto) {
		Product product = modelMapper.map(productDto, Product.class);
		if (productService.createProduct(product)) {
			return ResponseEntity.ok(HttpStatus.CREATED);
		}
		return ResponseEntity.ok(HttpStatus.BAD_GATEWAY);
	}

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(defaultValue = "0")  int page,
													 @RequestParam(defaultValue = "3") int size) {
        List<Product> getProducts = productService.getProducts(size, page);
        if (getProducts != null) {
            return new ResponseEntity<>(getProducts, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

	@GetMapping("/{product_code}")
	public ResponseEntity<Product> getProduct(@PathVariable("product_code") String productCode) {
		Product getProduct = productService.getProduct(productCode);
		if (getProduct != null) {
			return ResponseEntity.ok(getProduct);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/exist/{product_code}")
	public boolean checkProductExists(@PathVariable("product_code") String productCode) {
		boolean checkProductExists = productService.checkProductExists(productCode);
		if (checkProductExists) {
			return true;
		}
		return false;
	}

	@PutMapping("/{product_code}")
	public ResponseEntity<HttpStatus> updateProduct(@PathVariable("product_code") String productCode,@RequestBody Product product) {
		boolean updatedProduct = productService.updateProduct(productCode, product);
		if (updatedProduct) {
			return ResponseEntity.ok(HttpStatus.CREATED);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/{product_code}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("product_code") String productCode) {
		boolean updatedProduct = productService.deleteProduct(productCode);
		if (updatedProduct) {
			return ResponseEntity.ok(HttpStatus.CREATED);
		}
		return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}

}
