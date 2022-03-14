package com.orderservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order_line_items")
public class OrderLineItems {
    @Id
    private Integer id;
    private String productCode;
    private Integer quantity;
}
