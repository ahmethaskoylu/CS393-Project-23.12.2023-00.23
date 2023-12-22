package com.restaurant.dtos;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CategoryDTO {

    @Schema(description = "Name of the category", example = "Beverages")
    private String name;

    @ArraySchema(schema = @Schema(description = "List of products in this category", implementation = CategoryProductDTO.class))
    private List<CategoryProductDTO> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<CategoryProductDTO> products) {
        this.products = products;
    }
}

