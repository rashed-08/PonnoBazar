package com.web.inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/inventory")
public class InventoryController {

    @PostMapping
    public ResponseEntity<HttpStatus> createStock() {
        return null;
    }
}
