package com.restaurant.controller;

import com.restaurant.dtos.CategoryDTO;
import com.restaurant.dtos.ProductDTO;
import com.restaurant.entity.Category;
import com.restaurant.entity.Product;
import com.restaurant.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories", description = "The Categories API, providing access to category data.")
public class CategoryController {

    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public CategoryController(ModelMapper modelMapper, CategoryService categoryService) {
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Get All Categories", description = "Retrieve all categories.")
    public List<CategoryDTO> getAllCategories() {
        return modelMapper.map(categoryService.getAllCategories(), new TypeToken<List<CategoryDTO>>() {
        }.getType());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Category by ID", description = "Retrieve a category by its unique ID.")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return modelMapper.map(categoryService.getCategoryById(id), CategoryDTO.class);
    }

    @PostMapping
    @Operation(summary = "Create Category", description = "Create a new category.")
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return modelMapper.map(categoryService.createCategory(modelMapper.map(categoryDTO, Category.class)), CategoryDTO.class);
    }

    @PostMapping("/{categoryId}/addProduct")
    @Operation(summary = "Add Product to Category", description = "Add a product to a specified category.")
    public CategoryDTO addProductToCategory(@PathVariable Long categoryId, @RequestBody ProductDTO productDTO) {
        return modelMapper.map(categoryService.addProductToCategory(categoryId, modelMapper.map(productDTO, Product.class)), CategoryDTO.class);
    }
}
