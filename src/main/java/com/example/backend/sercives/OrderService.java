package com.example.backend.sercives;

import com.example.backend.dto.orderDtos.*;
import com.example.backend.entity.Product;
import com.example.backend.entity.User;
import com.example.backend.entity.order.*;
import com.example.backend.enums.OrderStatus;
import com.example.backend.exceptions.AuthException;
import com.example.backend.exceptions.OrderException;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.order.OrderRepository;
import com.example.backend.util.AppConvector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AppConvector convector;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, AppConvector convector) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.convector = convector;
    }

    public OrderDto findById(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException("There is no order with this id", HttpStatus.NOT_FOUND));
        return convector.convertToOrderDto(order);
    }

    public List<OrderDto> findAll(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(convector::convertToOrderDto).toList();
    }

    public List<OrderDto> findByStatus(OrderStatus status){
        List<Order> orders = orderRepository.findByStatus(status);
        return orders.stream().map(convector::convertToOrderDto).toList();
    }

    public List<OrderDto> findByUserId(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AuthException("There is no user with this id", HttpStatus.NOT_FOUND)
        );
        return orderRepository.findByUser(user).stream().map(convector::convertToOrderDto).toList();
    }

    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        Order order = convector.toOrder(createOrderDto);
        order.setStatus(OrderStatus.NEW);
        if (createOrderDto.getUserId() != -1){
            User user = userRepository.findById(
                    createOrderDto.getUserId()
            ).orElseThrow(
                    () -> new AuthException("There is no user with given id.",
                            HttpStatus.BAD_REQUEST));
            order.setUser(user);
        }
        order.setCreatedAt(new Date());
        Order preSaveOrder = orderRepository.save(order);
        AtomicReference<Double> totalSum = new AtomicReference<>(0.0);
        createOrderDto.getProducts().forEach(product -> {
            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            if (optionalProduct.isPresent()) {
                ProductInOrder productInOrder = new ProductInOrder();
                productInOrder.setProduct(optionalProduct.get());
                productInOrder.setAmount(product.getAmount());
                productInOrder.setOrder(preSaveOrder);
                order.addProduct(productInOrder);
                totalSum.updateAndGet(v -> v + product.getAmount() * optionalProduct.get().getPrice());
            }
        });
        order.setTotalSum(totalSum.get());
        Order savedOrder = orderRepository.save(order);
        return convector.convertToOrderDto(savedOrder);
    }
}
