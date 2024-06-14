package com.example.backend.dto.userDtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpDto {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String middleName;
    @NotNull
    private String phone;
    @NotNull
    private String email;
    @NotNull
    private char[] password;
}
