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
public class OrderBuyerInfoDto {

    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String phone;

}
