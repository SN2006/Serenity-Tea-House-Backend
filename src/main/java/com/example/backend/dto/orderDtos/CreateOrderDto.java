package com.example.backend.dto.orderDtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDto {
    @NotNull
    private Long userId;
    @NotNull
    private OrderAddressDto address;
    @NotNull
    private OrderBuyerInfoDto buyerInfo;
    @NotNull
    private CardInfoDto cardInfo;
    @NotNull
    private List<OrderProductPackage> products;
}
