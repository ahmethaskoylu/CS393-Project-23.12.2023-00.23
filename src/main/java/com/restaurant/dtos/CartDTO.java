package com.restaurant.dtos;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {

    private Long id;

    @Schema(description = "Total price of all products in the cart", example = "49.95")
    private BigDecimal totalPrice;

    @ArraySchema(schema = @Schema(implementation = OrderEntryDTO.class, description = "List of entries in the cart"))
    private List<OrderEntryDTO> entries;

    @Schema(description = "Customer who owns the cart", implementation = UserDTO.class)
    private UserDTO customer;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


    public UserDTO getCustomer() {
        return customer;
    }

    public void setCustomer(UserDTO customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<OrderEntryDTO> entries) {
        this.entries = entries;
    }
}
