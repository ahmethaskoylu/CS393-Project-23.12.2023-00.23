package com.restaurant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;
import com.restaurant.service.UserService;
import com.restaurant.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void createUserTest() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
    }

    @Test
    void getUserByIdTest() {
        Long userId = 1L;
        User mockUser = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User foundUser = userService.getUserById(userId);

        assertNotNull(foundUser);
    }

    @Test
    void getUserByIdNotFoundTest() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
    }

    @Test
    void getAllUsersTest() {
        User userOne = new User();
        User userTwo = new User();
        List<User> mockUsers = Arrays.asList(userOne, userTwo);
        when(userRepository.findAll()).thenReturn(mockUsers);

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
    }

    @Test
    void getUserByEmailTest() {
        String email = "test@example.com";
        User mockUser = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        User foundUser = userService.getUserByEmail(email);

        assertNotNull(foundUser);
    }

    @Test
    void getUserByEmailNotFoundTest() {
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserByEmail(email));
    }
}

