package com.orderservice.dto;

import com.orderservice.model.OrderLineItems;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxResponse {
    private String txId;
    private String status;
}
