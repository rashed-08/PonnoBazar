package com.orderservice.service.impl;

import com.orderservice.client.InventoryFeignClient;
import com.orderservice.client.ProductFeignClient;
import com.orderservice.dto.OrderDto;
import com.orderservice.dto.StockDto;
import com.orderservice.dto.TxResponse;
import com.orderservice.repository.OrderRepository;
import com.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductFeignClient productFeignClient;
    private final InventoryFeignClient inventoryFeignClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductFeignClient productFeignClient, InventoryFeignClient inventoryFeignClient) {
        this.orderRepository = orderRepository;
        this.productFeignClient = productFeignClient;
        this.inventoryFeignClient = inventoryFeignClient;
    }

    @Override
    public TxResponse placeOrder(OrderDto orderDto) {
        boolean isProductAvailable = orderDto.getOrderLineItems().stream().allMatch(x -> productFeignClient.checkProduct( x.getProductCode()));
        boolean isStockAvailable = orderDto.getOrderLineItems().stream().allMatch(x -> inventoryFeignClient.isStockAvailable(x.getProductCode(), x.getQuantity()));
        if (isProductAvailable && isStockAvailable) {
            return new TxResponse("1222dfs", "order success.");
        }
        return null;
    }
}
