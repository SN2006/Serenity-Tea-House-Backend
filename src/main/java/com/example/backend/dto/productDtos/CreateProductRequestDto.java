package com.example.backend.dto.productDtos;

import com.example.backend.validation.ProductName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequestDto {

    @NotNull
    @Size(min = 2, max = 200)
    @ProductName
    private String name;
    @NotNull
    @Size(min = 2)
    private String description;
    @NotNull
    private Float price;
    @NotNull
    private String type;
}
