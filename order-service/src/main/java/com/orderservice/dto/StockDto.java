package com.orderservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockDto {
    private String productCode;
    private int quantity;
}
