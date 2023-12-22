package com.restaurant.service;

import com.restaurant.dtos.PlaceOrderDTO;
import com.restaurant.entity.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(PlaceOrderDTO order, Long userId);

    Order getOrderById(Long id);

    List<Order> getAllOrders();
}
