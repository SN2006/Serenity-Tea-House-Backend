package com.example.backend.controllers;

import com.example.backend.dto.orderDtos.CreateOrderDto;
import com.example.backend.dto.orderDtos.OrderDto;
import com.example.backend.sercives.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

}
