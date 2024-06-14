package com.example.backend.dto.userDtos;

import com.example.backend.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EditUserDto {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String middleName;
    @NotNull
    private String phone;
    @NotNull
    private AddressDto address;

}
