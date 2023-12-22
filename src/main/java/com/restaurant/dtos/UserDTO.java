package com.restaurant.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserDTO {


    private Long id;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the user", example = "johndoe@example.com", required = true)
    private String email;

    @Schema(description = "Home address of the user", example = "123 Main Street, Anytown, USA")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
