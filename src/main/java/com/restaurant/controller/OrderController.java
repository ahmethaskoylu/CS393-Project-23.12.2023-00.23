package com.restaurant.controller;

import com.restaurant.dtos.OrderDTO;
import com.restaurant.dtos.PlaceOrderDTO;
import com.restaurant.entity.Order;
import com.restaurant.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "The Orders API, providing access to order data.")
public class OrderController {

    private final ModelMapper modelMapper;
    private final OrderService orderService;

    public OrderController(ModelMapper modelMapper, OrderService orderService) {
        this.modelMapper = modelMapper;
        this.orderService = orderService;
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create Order", description = "Order is created using the cart.")
    public OrderDTO createOrder(@RequestBody PlaceOrderDTO placeOrderDTO, @PathVariable Long userId) {
        return modelMapper.map(orderService.createOrder(placeOrderDTO, userId), OrderDTO.class);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Order by ID", description = "Retrieve an order by its unique ID.")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return modelMapper.map(orderService.getOrderById(id), OrderDTO.class);
    }

    @GetMapping
    @Operation(summary = "Get All Orders", description = "Retrieve a list of all orders.")
    public List<OrderDTO> getAllOrders() {
        return modelMapper.map(orderService.getAllOrders(), new TypeToken<List<OrderDTO>>() {
        }.getType());
    }
}

