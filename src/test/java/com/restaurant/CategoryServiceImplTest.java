package com.restaurant;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.restaurant.entity.Category;
import com.restaurant.entity.Product;
import com.restaurant.repository.CategoryRepository;
import com.restaurant.service.CategoryService;
import com.restaurant.service.ProductService;
import com.restaurant.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductService productService;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, productService);
    }

    @Test
    void createCategoryTest() {
        Category category = new Category();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category createdCategory = categoryService.createCategory(category);

        assertNotNull(createdCategory);
    }

    @Test
    void getCategoryByIdTest() {
        Long categoryId = 1L;
        Category mockCategory = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));

        Category foundCategory = categoryService.getCategoryById(categoryId);

        assertNotNull(foundCategory);
    }

    @Test
    void getCategoryByIdNotFoundTest() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoryService.getCategoryById(categoryId));
    }

    @Test
    void getAllCategoriesTest() {
        Category categoryOne = new Category();
        Category categoryTwo = new Category();
        List<Category> mockCategories = Arrays.asList(categoryOne, categoryTwo);
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<Category> categories = categoryService.getAllCategories();

        assertEquals(2, categories.size());
    }

    @Test
    void getCategoryByNameTest() {
        String name = "Beverages";
        Category mockCategory = new Category();
        when(categoryRepository.findByName(name)).thenReturn(mockCategory);

        Category foundCategory = categoryService.getCategoryByName(name);

        assertNotNull(foundCategory);
    }

    @Test
    void addProductToNewCategorySuccessfully() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setProducts(new ArrayList<>());

        Product product = new Product();
        product.setId(null);
        product.setName("Coke");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        when(productService.getAllProductsByCategory(any(Category.class))).thenReturn(List.of(product));

        Category result = categoryService.addProductToCategory(categoryId, product);

        assertNotNull(result);
        assertFalse(result.getProducts().isEmpty());
        assertEquals(product, result.getProducts().get(0));
    }

    @Test
    void throwExceptionWhenCategoryNotFound() {
        Long categoryId = 1L;
        Product product = new Product();
        product.setId(null);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoryService.addProductToCategory(categoryId, product));
    }

    @Test
    void throwExceptionWhenProductAlreadyExists() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);

        Product product = new Product();
        product.setId(2L);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        assertThrows(RuntimeException.class, () -> categoryService.addProductToCategory(categoryId, product));
    }
}
