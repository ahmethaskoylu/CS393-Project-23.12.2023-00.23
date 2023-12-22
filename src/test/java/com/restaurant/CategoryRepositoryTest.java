package com.restaurant;

import com.restaurant.entity.Category;
import com.restaurant.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Harici veritabanını kullan
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateAndFindCategory() {
        Category category = new Category("Beverages");
        category = categoryRepository.save(category);

        Optional<Category> foundCategory = categoryRepository.findById(category.getId());
        Assertions.assertTrue(foundCategory.isPresent());
        Assertions.assertEquals("Beverages", foundCategory.get().getName());
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category("Beverages");
        category = categoryRepository.save(category);

        category.setName("Desserts");
        category = categoryRepository.save(category);

        Optional<Category> updatedCategory = categoryRepository.findById(category.getId());
        Assertions.assertTrue(updatedCategory.isPresent());
        Assertions.assertEquals("Desserts", updatedCategory.get().getName());
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category("Beverages");
        category = categoryRepository.save(category);

        categoryRepository.delete(category);

        Optional<Category> deletedCategory = categoryRepository.findById(category.getId());
        Assertions.assertFalse(deletedCategory.isPresent());
    }
}
