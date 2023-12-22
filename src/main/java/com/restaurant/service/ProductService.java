package com.restaurant.service;

import com.restaurant.entity.Category;
import com.restaurant.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    List<Product> getAllProductsByCategory(Category category);
}
