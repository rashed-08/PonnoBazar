package com.web.inventory.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockDTO {
    private String productCode;
    private int quantity;
}
