package com.restaurant.service;

import com.restaurant.entity.Category;
import com.restaurant.entity.Product;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category getCategoryByName(String name);

    Category addProductToCategory(Long categoryId, Product productId);
}
