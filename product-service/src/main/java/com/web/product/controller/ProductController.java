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
    public ResponseEntity<List<Product>> getProducts(@RequestParam("size") int size, @RequestParam("page")  int page) {
        List<Product> getProducts = productService.getProducts(size, page);
        if (getProducts != null) {
            return new ResponseEntity<>(getProducts, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@GetMapping("/{product_code}")
	public ResponseEntity<Product> getProduct(@PathVariable("product_code") String productCode) {
		Product getProduct = productService.getProduct(productCode);
		System.out.println("product code: " + productCode);
		if (getProduct != null) {
			return ResponseEntity.ok(getProduct);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
