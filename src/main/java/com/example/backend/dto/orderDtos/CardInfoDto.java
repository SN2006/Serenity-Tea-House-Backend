package com.example.backend.dto.orderDtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoDto {

    @Pattern(regexp = "[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}")
    private String cardNumber;
    @NotNull
    private String nameOnTheCard;
    @Pattern(regexp = "[0-9]{2}/[0-9]{2}")
    private String validityPeriod;
    @Pattern(regexp = "[0-9]{3}")
    private String cvv;

}
