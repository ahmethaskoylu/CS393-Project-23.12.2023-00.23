package com.restaurant;

import com.restaurant.dtos.PlaceOrderDTO;
import com.restaurant.entity.Cart;
import com.restaurant.entity.Order;
import com.restaurant.entity.User;
import com.restaurant.repository.OrderEntryRepository;
import com.restaurant.repository.OrderRepository;
import com.restaurant.service.CartService;
import com.restaurant.service.OrderService;
import com.restaurant.service.UserService;
import com.restaurant.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private CartService cartService;

    @Mock
    private OrderEntryRepository orderEntryRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderServiceImpl(orderRepository, userService, cartService, orderEntryRepository);
    }

    @Test
    void createOrderTest() {
        Long userId = 1L;
        Long cartId = 2L;
        User user = new User();
        user.setId(userId);
        user.setAddress("Test Address");
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setEntries(new ArrayList<>()); // assume the cart has products
        PlaceOrderDTO placeOrderDTO = new PlaceOrderDTO();
        placeOrderDTO.setCartId(cartId);

        when(userService.getUserById(userId)).thenReturn(user);
        when(cartService.getCartById(cartId)).thenReturn(cart);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order createdOrder = orderService.createOrder(placeOrderDTO, userId);

        assertNotNull(createdOrder);
        assertEquals("PLACED", createdOrder.getOrderStatus());
        assertEquals(user.getAddress(), createdOrder.getDeliveryAdress());
        verify(cartService, times(1)).deleteCart(cart);
    }

    @Test
    void createOrderWithNonExistentUserTest() {
        Long userId = 1L;
        PlaceOrderDTO placeOrderDTO = new PlaceOrderDTO();
        placeOrderDTO.setCartId(2L);

        when(userService.getUserById(userId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> orderService.createOrder(placeOrderDTO, userId));
    }

    @Test
    void getOrderByIdTest() {
        Long orderId = 1L;
        Order mockOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        Order foundOrder = orderService.getOrderById(orderId);

        assertNotNull(foundOrder);
    }

    @Test
    void getOrderByIdNotFoundTest() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getOrderById(orderId));
    }

    @Test
    void getAllOrdersTest() {
        Order orderOne = new Order();
        Order orderTwo = new Order();
        List<Order> mockOrders = Arrays.asList(orderOne, orderTwo);
        when(orderRepository.findAll()).thenReturn(mockOrders);

        List<Order> orders = orderService.getAllOrders();

        assertEquals(2, orders.size());
    }
}

