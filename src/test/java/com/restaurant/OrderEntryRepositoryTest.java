package com.restaurant;

import com.restaurant.entity.OrderEntry;
import com.restaurant.entity.Product;
import com.restaurant.repository.OrderEntryRepository;
import com.restaurant.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Harici veritabanını kullan
public class OrderEntryRepositoryTest {

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private ProductRepository productRepository;

    private OrderEntry testOrderEntry;
    private Product testProduct;

    @BeforeEach
    public void setup() {
        testProduct = new Product("Test Product", "Description", new BigDecimal("10.00"), null);
        testProduct = productRepository.save(testProduct);

        testOrderEntry = new OrderEntry();
        testOrderEntry.setProduct(testProduct);
        testOrderEntry.setQuantity(2);
        testOrderEntry.setTotalPrice(new BigDecimal("20.00"));
    }

    @Test
    public void testCreateAndSaveOrderEntry() {
        OrderEntry savedOrderEntry = orderEntryRepository.save(testOrderEntry);
        Assertions.assertNotNull(savedOrderEntry);
        Assertions.assertNotNull(savedOrderEntry.getId());
    }

    @Test
    public void testReadOrderEntry() {
        OrderEntry savedOrderEntry = orderEntryRepository.save(testOrderEntry);
        OrderEntry foundOrderEntry = orderEntryRepository.findById(savedOrderEntry.getId()).orElse(null);
        Assertions.assertNotNull(foundOrderEntry);
        Assertions.assertEquals(savedOrderEntry.getId(), foundOrderEntry.getId());
    }

    @Test
    public void testUpdateOrderEntry() {
        OrderEntry savedOrderEntry = orderEntryRepository.save(testOrderEntry);
        savedOrderEntry.setQuantity(3);
        OrderEntry updatedOrderEntry = orderEntryRepository.save(savedOrderEntry);

        Assertions.assertNotNull(updatedOrderEntry);
        Assertions.assertEquals(3, updatedOrderEntry.getQuantity());
    }

    @Test
    public void testDeleteOrderEntry() {
        OrderEntry savedOrderEntry = orderEntryRepository.save(testOrderEntry);
        Long orderEntryId = savedOrderEntry.getId();
        orderEntryRepository.delete(savedOrderEntry);

        assertFalse(orderEntryRepository.findById(orderEntryId).isPresent());
    }
}
