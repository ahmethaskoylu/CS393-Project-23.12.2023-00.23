package com.restaurant;

import com.restaurant.entity.User;
import com.restaurant.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");
        testUser.setAddress("123 Test Street");
        testUser.setPassword("password123");
    }

    @Test
    public void testCreateAndSaveUser() {
        User savedUser = userRepository.save(testUser);
        Assertions.assertNotNull(savedUser);
        Assertions.assertNotNull(savedUser.getId());
    }

    @Test
    public void testReadUser() {
        User savedUser = userRepository.save(testUser);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(savedUser.getId(), foundUser.getId());
    }

    @Test
    public void testUpdateUser() {
        User savedUser = userRepository.save(testUser);
        savedUser.setEmail("updateduser@example.com");
        User updatedUser = userRepository.save(savedUser);

        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals("updateduser@example.com", updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        User savedUser = userRepository.save(testUser);
        Long userId = savedUser.getId();
        userRepository.delete(savedUser);

        assertFalse(userRepository.findById(userId).isPresent());
    }
}
