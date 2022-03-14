package com.orderservice.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Integer paymentId;
    private String paymentStatus;
    private String orderId;
    private Double amount;
}
