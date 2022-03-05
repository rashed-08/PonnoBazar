package com.orderservice.dto;

import com.orderservice.model.OrderLineItems;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private List<OrderLineItems> orderLineItems;
}
