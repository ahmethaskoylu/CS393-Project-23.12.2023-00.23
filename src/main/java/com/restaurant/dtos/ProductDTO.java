package com.restaurant.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ProductDTO {

    @Schema(description = "ID of the product", required = false)
    private Long id;

    @Schema(description = "Name of the product", example = "Margherita Pizza", required = true)
    private String name;

    @Schema(description = "Description of the product", example = "Classic Margherita with fresh mozzarella and basil", required = false)
    private String description;

    @Schema(description = "Price of the product", example = "9.99", required = true)
    private BigDecimal price;

    @Schema(description = "Category of the product", implementation = CategoryDTO.class)
    @JsonIgnore
    private CategoryDTO category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
