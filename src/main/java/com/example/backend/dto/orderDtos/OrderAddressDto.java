package com.example.backend.dto.orderDtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddressDto {

    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String address;

}
