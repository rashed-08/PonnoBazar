package com.orderservice.service.impl;

import com.orderservice.client.InventoryFeignClient;
import com.orderservice.client.ProductFeignClient;
import com.orderservice.dto.OrderDto;
import com.orderservice.dto.TxResponse;
import com.orderservice.repository.OrderRepository;
import com.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ProductFeignClient productFeignClient;
    private final InventoryFeignClient inventoryFeignClient;
    private final KafkaTemplate<String, OrderDto> kafkaTemplate;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductFeignClient productFeignClient,
                            InventoryFeignClient inventoryFeignClient,
                            KafkaTemplate<String, OrderDto> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.productFeignClient = productFeignClient;
        this.inventoryFeignClient = inventoryFeignClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public TxResponse placeOrder(OrderDto orderDto) {
        boolean isProductAvailable = orderDto.getOrderLineItems().stream().allMatch(x -> productFeignClient.checkProduct( x.getProductCode()));
        boolean isStockAvailable = orderDto.getOrderLineItems().stream().allMatch(x -> inventoryFeignClient.isStockAvailable(x.getProductCode(), x.getQuantity()));
        if (isProductAvailable && isStockAvailable) {
            logger.info("-----------------Producing Order Message ------------------------");
            kafkaTemplate.send("order", orderDto);
        }
        return null;
    }

    @KafkaListener(
            topics = "inventory",
            groupId = "groupId"
    )
    @Override
    public boolean orderStatusUpdate(String message) {
        logger.info("-------------------------Receiving inventory message -------------------");
        System.out.println("Message received: " + message);
        return false;
    }
}
