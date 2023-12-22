package com.restaurant.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public class PlaceOrderDTO {

    @Schema(description = "Identifier of the cart for placing an order", required = true)
    private Long cartId;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
}
