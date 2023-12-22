package com.restaurant.dtos;

import com.restaurant.entity.User;

import java.util.List;

public class OrderDTO {

    private String orderStatus;
    private String deliveryAdress;
    private UserDTO customer;
    private List<OrderEntryDTO> entries;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryAdress() {
        return deliveryAdress;
    }

    public void setDeliveryAdress(String deliveryAdress) {
        this.deliveryAdress = deliveryAdress;
    }

    public UserDTO getCustomer() {
        return customer;
    }

    public void setCustomer(UserDTO customer) {
        this.customer = customer;
    }

    public List<OrderEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<OrderEntryDTO> entries) {
        this.entries = entries;
    }
}
