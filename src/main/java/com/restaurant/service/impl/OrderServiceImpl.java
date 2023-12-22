package com.restaurant.service.impl;

import com.restaurant.dtos.PlaceOrderDTO;
import com.restaurant.entity.Cart;
import com.restaurant.entity.Order;
import com.restaurant.entity.OrderEntry;
import com.restaurant.entity.User;
import com.restaurant.repository.OrderEntryRepository;
import com.restaurant.repository.OrderRepository;
import com.restaurant.service.CartService;
import com.restaurant.service.OrderService;
import com.restaurant.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CartService cartService;
    private final OrderEntryRepository orderEntryRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, CartService cartService, OrderEntryRepository orderEntryRepository) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.orderEntryRepository = orderEntryRepository;
    }

    @Override
    public Order createOrder(PlaceOrderDTO placeOrderDTO, Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        Cart cart = cartService.getCartById(placeOrderDTO.getCartId());

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        if (cart.getEntries() == null) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderStatus("PLACED");
        order.setCustomer(user);
        order.setDeliveryAdress(user.getAddress());

        List<OrderEntry> entries = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderEntry entry : cart.getEntries()) {
            totalPrice.add(entry.getTotalPrice());
            entries.add(new OrderEntry(entry.getProduct(), entry.getQuantity(), entry.getTotalPrice()));
        }


        order.setEntries(entries);
        orderEntryRepository.saveAll(entries);
        order.setTotalPrice(totalPrice);
        order = orderRepository.save(order);
        cartService.deleteCart(cart); // delete cart after order is placed

        return order;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
