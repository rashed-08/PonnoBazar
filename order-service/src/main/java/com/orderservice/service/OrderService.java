package com.orderservice.service;

import com.orderservice.dto.OrderDto;
import com.orderservice.dto.TxResponse;

public interface OrderService {
    TxResponse placeOrder(OrderDto orders);
    boolean orderStatusUpdate(String message);
}
