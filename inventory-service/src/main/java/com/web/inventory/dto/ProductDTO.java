package com.web.inventory.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productCode;
    private String productName;
    private String skuCode;
    private String unitPrice;
    private String sellPrice;
    private String image;
}
