package com.example.backend.dto.orderDtos;

import com.example.backend.dto.userDtos.AddressDto;
import com.example.backend.dto.userDtos.UserDto;
import com.example.backend.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private OrderAddressDto address;
    private OrderBuyerInfoDto buyerInfo;
    private CardInfoDto cardInfo;
    private UserDto user;
    private OrderStatus status;
    private List<ProductInOrderDto> products;

    public void addProduct(ProductInOrderDto product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }
}
