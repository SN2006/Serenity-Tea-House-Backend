package com.example.backend.sercives;

import com.example.backend.dto.orderDtos.*;
import com.example.backend.entity.Product;
import com.example.backend.entity.User;
import com.example.backend.entity.order.*;
import com.example.backend.enums.OrderStatus;
import com.example.backend.exceptions.AuthException;
import com.example.backend.repositories.ProductRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.order.OrderRepository;
import com.example.backend.util.AppConvector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        Order order = convector.toOrder(createOrderDto);
        order.setStatus(OrderStatus.IN_PROCESSING);
        if (createOrderDto.getUserId() != -1){
            User user = userRepository.findById(
                    createOrderDto.getUserId()
            ).orElseThrow(
                    () -> new AuthException("There is no user with given id.",
                            HttpStatus.BAD_REQUEST));
            order.setUser(user);
        }
        Order preSaveOrder = orderRepository.save(order);
        createOrderDto.getProducts().forEach(product -> {
            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            if (optionalProduct.isPresent()) {
                ProductInOrder productInOrder = new ProductInOrder();
                productInOrder.setProduct(optionalProduct.get());
                productInOrder.setAmount(product.getAmount());
                productInOrder.setOrder(preSaveOrder);
                order.addProduct(productInOrder);
            }
        });
        Order savedOrder = orderRepository.save(order);
        return convector.convertToOrderDto(savedOrder);
    }
}
