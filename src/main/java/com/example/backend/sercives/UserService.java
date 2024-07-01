package com.example.backend.sercives;

import com.example.backend.dto.userDtos.*;
import com.example.backend.entity.Address;
import com.example.backend.entity.User;
import com.example.backend.enums.Role;
import com.example.backend.exceptions.AuthException;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.order.OrderRepository;
import com.example.backend.util.AppConvector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppConvector convector;

    @Autowired
    public UserService(UserRepository userRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder, AppConvector convector) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
        this.convector = convector;
    }

    public UserDto login(CredentialsDto credentialsDto){
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AuthException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(
                CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword()
        )){
            return convector.convertToUserDto(user);
        }

        throw new AuthException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto){
        Optional<User> optionalUser = userRepository.findByEmail(signUpDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new AuthException("Email already in use", HttpStatus.BAD_REQUEST);
        }

        User user = convector.convertToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.getPassword())));
        user.setRole(Role.USER);
        Address address = new Address();
        address.setCountry("Ukraine");
        address.setCity("");
        user.setAddress(address);

        Date now = new Date();
        user.setCreatedAt(now);
        user.setLastVisit(now);

        User savedUser = userRepository.save(user);

        return convector.convertToUserDto(savedUser);
    }

    public UserDto edit(Long id, EditUserDto editUserDto){
        User editUser = convector.convertToUser(editUserDto);
        User userFromDb = userRepository.findById(id).orElseThrow(
                () -> new AuthException("Unknown user", HttpStatus.NOT_FOUND)
        );
        editUser.setId(userFromDb.getId());
        editUser.setEmail(userFromDb.getEmail());
        editUser.setPassword(userFromDb.getPassword());
        editUser.setRole(userFromDb.getRole());

        User savedUser = userRepository.save(editUser);
        return convector.convertToUserDto(savedUser);
    }

    public UserDto findUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("Unknown user", HttpStatus.NOT_FOUND));

        user.setLastVisit(new Date());
        userRepository.save(user);
        return convector.convertToUserDto(user);
    }

    public List<UserDto> findUsersByRole(Role role){
        List<User> users = userRepository.findByRole(role);
        return users.stream()
                .map(user -> {
                    UserDto userDto = convector.convertToUserDto(user);
                    userDto.setCountOfOrder(
                            orderRepository.countByUser(user)
                    );
                    return userDto;
                }).toList();
    }
}
