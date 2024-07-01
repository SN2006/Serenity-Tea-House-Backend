package com.example.backend.controllers;

import com.example.backend.dto.orderDtos.CreateOrderDto;
import com.example.backend.dto.orderDtos.OrderDto;
import com.example.backend.dto.userDtos.UserDto;
import com.example.backend.enums.OrderStatus;
import com.example.backend.sercives.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<OrderDto>> getAllOrdersByStatus(@RequestParam("status") String status) {
        return switch (status) {
            case "NEW" -> ResponseEntity.ok(orderService.findByStatus(OrderStatus.NEW));
            case "COMPLETED" -> ResponseEntity.ok(orderService.findByStatus(OrderStatus.COMPLETED));
            default -> ResponseEntity.ok(orderService.findAll());
        };
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/by-current-user")
    public ResponseEntity<List<OrderDto>> getOrderForCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) authentication.getPrincipal();
        return ResponseEntity.ok(orderService.findByUserId(userDto.getId()));
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

}
