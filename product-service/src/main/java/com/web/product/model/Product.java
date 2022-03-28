package com.web.product.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product implements Serializable {

	@Id
	@NotBlank(message = "Product name cannot be blank.")
	@Min(value = 2, message = "Product name cannot be less than 2 characters")
	@Max(value = 8, message = "Product name cannot be greater than 8 characters")
	private String productCode;
	@NotBlank(message = "Product name cannot be blank.")
	@Min(value = 2, message = "Product name cannot be less than 2 characters")
	@Max(value = 20, message = "Product name cannot be greater than 20 characters")
	private String productName;
	private String skuCode;
	private int unitPrice;
	private int sellPrice;
	private String image;
	private Boolean isActive;
}
