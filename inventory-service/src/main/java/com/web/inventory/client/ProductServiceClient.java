package com.web.inventory.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service")
public class ProductServiceClient {
}
