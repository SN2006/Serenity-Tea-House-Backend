package com.example.backend.controllers;

import com.example.backend.dto.userDtos.EditUserDto;
import com.example.backend.dto.userDtos.UserDto;
import com.example.backend.enums.Role;
import com.example.backend.sercives.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = {"http://localhost:3000/"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/customers")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(userService.findUsersByRole(Role.USER));
    }

    @GetMapping("/admins-without-me")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<UserDto>> getAdmins(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) authentication.getPrincipal();
        return ResponseEntity.ok(userService.findByRoleWithoutId(Role.ADMIN, userDto.getId()));
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) authentication.getPrincipal();
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<UserDto> getAdminUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = (UserDto) authentication.getPrincipal();
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id,
                                              @RequestBody EditUserDto editUserDto) {
        return ResponseEntity.ok(
                userService.edit(id, editUserDto)
        );
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/nicknames/without-id/{userId}")
    public ResponseEntity<List<String>> getNicknames(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getNicknamesWithoutId(userId));
    }

}
