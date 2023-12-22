package com.restaurant.controller;

import com.restaurant.dtos.ProductDTO;
import com.restaurant.entity.Product;
import com.restaurant.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "The Products API, providing access to product data.")
public class ProductController {

    private final ModelMapper modelMapper;
    private final ProductService productService;

    public ProductController(ModelMapper modelMapper, ProductService productService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get Products", description = "Retrieve a list of all products in the menu.")
    public List<ProductDTO> getProducts() {
        return modelMapper.map(productService.getAllProducts(), new TypeToken<List<ProductDTO>>() {
        }.getType());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Product by ID", description = "Retrieve a product by its unique ID.")
    public ProductDTO getProductById(@PathVariable Long id) {
        return modelMapper.map(productService.getProductById(id), ProductDTO.class);
    }

    @PostMapping
    @Operation(summary = "Create Product", description = "Create a new product and add it to the menu.")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return modelMapper.map(productService.createProduct(modelMapper.map(productDTO, Product.class)), ProductDTO.class);
    }
}
