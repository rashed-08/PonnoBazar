package com.orderservice.repository;

import com.orderservice.model.OrderLineItems;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<OrderLineItems, Integer> {
}
