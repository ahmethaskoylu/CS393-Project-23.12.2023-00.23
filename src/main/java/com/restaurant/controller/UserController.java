package com.restaurant.controller;

import com.restaurant.dtos.UserDTO;
import com.restaurant.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "The Users API, providing access to user data.")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create User", description = "Create a new user.")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return modelMapper.map(userService.createUser(modelMapper.map(userDTO, com.restaurant.entity.User.class)), UserDTO.class);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", description = "Retrieve a user by their unique ID.")
    public UserDTO getUserById(@PathVariable Long id) {
        return modelMapper.map(userService.getUserById(id), UserDTO.class);
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Retrieve a list of all users.")
    public List<UserDTO> getAllUsers() {
        return modelMapper.map(userService.getAllUsers(), new TypeToken<List<UserDTO>>() {}.getType());
    }
}

