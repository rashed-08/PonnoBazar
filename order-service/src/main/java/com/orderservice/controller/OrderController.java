package com.orderservice.controller;

import com.orderservice.dto.OrderDto;
import com.orderservice.dto.TxResponse;
import com.orderservice.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/order")
public class OrderController {

    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public TxResponse placeOrder(@RequestBody OrderDto orders) {
        TxResponse response = orderService.placeOrder(orders);
        return response;
    }
}
