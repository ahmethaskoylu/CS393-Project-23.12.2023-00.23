package com.restaurant.config;

import com.restaurant.entity.Category;
import com.restaurant.entity.Product;
import com.restaurant.entity.User;
import com.restaurant.service.CategoryService;
import com.restaurant.service.ProductService;
import com.restaurant.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;

@Component
public class DatabaseLoader implements CommandLineRunner {


    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    public DatabaseLoader(CategoryService categoryService, ProductService productService, UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) {
        if (CollectionUtils.isEmpty(userService.getAllUsers())) {
            User user = new User();
            user.setName("Alison Customer");
            user.setEmail("alison@customer.com");
            user.setPassword("123456");//if spring booot securtiy enabled, you must encrypt password
            user.setAddress("DUMMY ADDRESS");
            userService.createUser(user);
        }

        if (CollectionUtils.isEmpty(categoryService.getAllCategories())) {
            categoryService.createCategory(new Category("Pizza"));
            categoryService.createCategory(new Category("Pasta"));
            categoryService.createCategory(new Category("Salad"));
            categoryService.createCategory(new Category("Dessert"));
            categoryService.createCategory(new Category("Drink"));
        }
        if (CollectionUtils.isEmpty(productService.getAllProducts())) {

            Category category = categoryService.getCategoryByName("Pizza");

            if (category == null) {
                category = new Category("Pizza");
                categoryService.createCategory(category);
                category = categoryService.getCategoryByName("Pizza");
            }

            productService.createProduct(new Product("Pizza Margherita", "Pizza Margherita", BigDecimal.valueOf(10.0), category));
            productService.createProduct(new Product("Pizza Pepperoni", "Pizza Pepperoni", BigDecimal.valueOf(12.0), category));
            productService.createProduct(new Product("Pizza Quattro Formaggi", "Pizza Quattro Formaggi", BigDecimal.valueOf(15.0), category));
            productService.createProduct(new Product("Pizza Capricciosa", "Pizza Capricciosa", BigDecimal.valueOf(13.0), category));
            productService.createProduct(new Product("Pizza Marinara", "Pizza Marinara", BigDecimal.valueOf(11.0), category));
            productService.createProduct(new Product("Pizza Diavola", "Pizza Diavola", BigDecimal.valueOf(14.0), category));
            productService.createProduct(new Product("Pizza Napoletana", "Pizza Napoletana", BigDecimal.valueOf(12.0), category));
            productService.createProduct(new Product("Pizza Siciliana", "Pizza Siciliana", BigDecimal.valueOf(13.0), category));
            productService.createProduct(new Product("Pizza Romana", "Pizza Romana", BigDecimal.valueOf(14.0), category));
            productService.createProduct(new Product("Pizza Calzone", "Pizza Calzone", BigDecimal.valueOf(15.0), category));
            productService.createProduct(new Product("Pizza Focaccia", "Pizza Focaccia", BigDecimal.valueOf(16.0), category));
            productService.createProduct(new Product("Pizza Pugliese", "Pizza Pugliese", BigDecimal.valueOf(17.0), category));
            productService.createProduct(new Product("Pizza Montanara", "Pizza Montanara", BigDecimal.valueOf(18.0), category));
            productService.createProduct(new Product("Pizza Emilia", "Pizza Emilia", BigDecimal.valueOf(19.0), category));
            productService.createProduct(new Product("Pizza Sicilia", "Pizza Sicilia", BigDecimal.valueOf(20.0), category));
            productService.createProduct(new Product("Pizza Sarda", "Pizza Sarda", BigDecimal.valueOf(21.0), category));
            productService.createProduct(new Product("Pizza Viennese", "Pizza Viennese", BigDecimal.valueOf(22.0), category));
        }
    }
}
