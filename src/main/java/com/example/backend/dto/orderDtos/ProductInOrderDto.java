package com.example.backend.dto.orderDtos;

import com.example.backend.dto.productDtos.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInOrderDto {

    private Integer amount;
    private ProductDto product;

}
