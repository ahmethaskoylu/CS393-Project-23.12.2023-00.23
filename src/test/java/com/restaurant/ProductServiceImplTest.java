package com.restaurant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.restaurant.entity.Product;
import com.restaurant.repository.ProductRepository;
import com.restaurant.service.ProductService;
import com.restaurant.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void createProductTest() {
        Product product = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
    }

    @Test
    void getProductByIdTest() {
        Long productId = 1L;
        Product mockProduct = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Product foundProduct = productService.getProductById(productId);

        assertNotNull(foundProduct);
    }

    @Test
    void getProductByIdNotFoundTest() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById(productId));
    }

    @Test
    void getAllProductsTest() {
        Product productOne = new Product();
        Product productTwo = new Product();
        List<Product> mockProducts = Arrays.asList(productOne, productTwo);
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
    }
}

