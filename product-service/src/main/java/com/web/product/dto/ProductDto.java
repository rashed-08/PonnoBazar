package com.web.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ProductDto {

	@NotNull(message = "Product name can't empty")
	@Size(min = 2, max = 30, message = "Product name can't be less than 2 characters")
	private String productName;
	private String productCode;
	private int unitPrice;
}
