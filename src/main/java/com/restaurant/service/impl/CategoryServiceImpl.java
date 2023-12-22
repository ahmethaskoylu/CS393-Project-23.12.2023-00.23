package com.restaurant.service.impl;

import com.restaurant.entity.Category;
import com.restaurant.entity.Product;
import com.restaurant.repository.CategoryRepository;
import com.restaurant.service.CategoryService;
import com.restaurant.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @Override
    public Category createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()) != null) {
            throw new RuntimeException("Category already exists");
        }
        List<Product> products = new ArrayList<>();

        for (Product product : category.getProducts()) {
            product.setId(null);
            products.add(product);
        }
        category.setProducts(products);
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setProducts(productService.getAllProductsByCategory(category));
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            category.setProducts(productService.getAllProductsByCategory(category));
        }

        return categories;
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category addProductToCategory(Long categoryId, Product product) {
        Category category = getCategoryById(categoryId);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        if (product.getId() != null && !product.getId().equals(0l)) {
            throw new RuntimeException("Product already exists");
        } else {
            product.setId(null);
            product.setCategory(category);
            product = productService.createProduct(product);
        }
        category.setProducts(productService.getAllProductsByCategory(category));
        if (CollectionUtils.isEmpty(category.getProducts())) {
            List<Product> products = new ArrayList<>();
            products.add(product);
            category.setProducts(products);
        } else {
            try {
                category.getProducts().add(product);
            } catch (Exception e) {
            }
        }
        category.setProducts(productService.getAllProductsByCategory(category));
        return category;
    }
}
