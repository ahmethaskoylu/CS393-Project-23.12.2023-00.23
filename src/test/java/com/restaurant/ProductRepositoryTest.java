package com.restaurant;

import com.restaurant.entity.Product;
import com.restaurant.entity.Category;
import com.restaurant.repository.CategoryRepository;
import com.restaurant.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Product testProduct;
    private Category testCategory;

    @BeforeEach
    public void setup() {
        testCategory = new Category("Test Category");
        testCategory = categoryRepository.save(testCategory);

        testProduct = new Product("Test Product", "Description", new BigDecimal("10.00"), testCategory);
    }

    @Test
    public void testCreateAndSaveProduct() {
        Product savedProduct = productRepository.save(testProduct);
        Assertions.assertNotNull(savedProduct);
        Assertions.assertNotNull(savedProduct.getId());
    }

    @Test
    public void testReadProduct() {
        Product savedProduct = productRepository.save(testProduct);
        Product foundProduct = productRepository.findById(savedProduct.getId()).orElse(null);
        Assertions.assertNotNull(foundProduct);
        Assertions.assertEquals(savedProduct.getId(), foundProduct.getId());
    }

    @Test
    public void testUpdateProduct() {
        Product savedProduct = productRepository.save(testProduct);
        savedProduct.setPrice(new BigDecimal("12.00"));
        Product updatedProduct = productRepository.save(savedProduct);

        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals(new BigDecimal("12.00"), updatedProduct.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        Product savedProduct = productRepository.save(testProduct);
        Long productId = savedProduct.getId();
        productRepository.delete(savedProduct);

        assertFalse(productRepository.findById(productId).isPresent());
    }
}
