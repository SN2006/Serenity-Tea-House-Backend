package com.example.backend.dto.userDtos;

import com.example.backend.enums.Role;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String middleName;
    private String email;
    private String phone;
    private Role role;
    private String token;
    private AddressDto address;
    private Date createdAt;
    private Date lastVisit;
    private Integer countOfOrder;

}
