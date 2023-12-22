package com.restaurant;

import com.restaurant.entity.Order;
import com.restaurant.entity.User;
import com.restaurant.repository.OrderRepository;
import com.restaurant.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Harici veritabanını kullan
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    private Order testOrder;
    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser = userRepository.save(testUser);

        testOrder = new Order();
        testOrder.setCustomer(testUser);
        testOrder.setDeliveryAdress("123 Test Street");
        testOrder.setTotalPrice(new BigDecimal("100.00"));
        testOrder.setOrderStatus("CREATED");
    }

    @Test
    public void testCreateAndSaveOrder() {
        Order savedOrder = orderRepository.save(testOrder);
        Assertions.assertNotNull(savedOrder);
        Assertions.assertNotNull(savedOrder.getId());
    }

    @Test
    public void testReadOrder() {
        Order savedOrder = orderRepository.save(testOrder);
        Order foundOrder = orderRepository.findById(savedOrder.getId()).orElse(null);
        Assertions.assertNotNull(foundOrder);
        Assertions.assertEquals(savedOrder.getId(), foundOrder.getId());
    }

    @Test
    public void testUpdateOrder() {
        Order savedOrder = orderRepository.save(testOrder);
        savedOrder.setOrderStatus("UPDATED");
        Order updatedOrder = orderRepository.save(savedOrder);

        Assertions.assertNotNull(updatedOrder);
        Assertions.assertEquals("UPDATED", updatedOrder.getOrderStatus());
    }

    @Test
    public void testDeleteOrder() {
        Order savedOrder = orderRepository.save(testOrder);
        Long orderId = savedOrder.getId();
        orderRepository.delete(savedOrder);

        assertFalse(orderRepository.findById(orderId).isPresent());
    }
}
