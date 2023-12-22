package com.restaurant.controller;

import com.restaurant.dtos.CartDTO;
import com.restaurant.entity.Cart;
import com.restaurant.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts/{userId}")
@Tag(name = "Carts", description = "The Carts API, providing access to cart data.")
public class CartController {

    private final ModelMapper modelMapper;
    private final CartService cartService;

    public CartController(ModelMapper modelMapper, CartService cartService) {
        this.modelMapper = modelMapper;
        this.cartService = cartService;
    }

    @PostMapping
    @Operation(summary = "Create Cart", description = "Create a new cart for a customer.")
    public CartDTO createCart(@RequestBody CartDTO cartDTO, @PathVariable Long userId) {
        return modelMapper.map(cartService.createCart(modelMapper.map(cartDTO, Cart.class), userId), CartDTO.class);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Cart by ID", description = "Retrieve a cart by its unique ID.")
    public CartDTO getCartById(@PathVariable Long id, @PathVariable Long userId) {
        return modelMapper.map(cartService.getCartById(id), CartDTO.class);
    }

    @GetMapping("/{cartId}/addProduct")
    @Operation(summary = "Add Product to Cart", description = "Add a product to a specified cart.")
    public CartDTO addProductToCart(@PathVariable Long cartId, @RequestParam Long productId, @PathVariable Long userId) {
        return modelMapper.map(cartService.addProductToCart(cartId, productId), CartDTO.class);
    }
}
