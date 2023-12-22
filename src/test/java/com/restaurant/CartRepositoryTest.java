package com.restaurant;

import com.restaurant.entity.Cart;
import com.restaurant.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Harici veritabanını kullan
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void testCreateAndFindCart() {
        Cart cart = new Cart();
        cart.setTotalPrice(new BigDecimal("100.00"));
        cart = cartRepository.save(cart);

        Optional<Cart> foundCart = cartRepository.findById(cart.getId());
        assertTrue(foundCart.isPresent());
        assertEquals(new BigDecimal("100.00"), foundCart.get().getTotalPrice());
    }

    @Test
    public void testUpdateCart() {
        Cart cart = new Cart();
        cart.setTotalPrice(new BigDecimal("100.00"));
        cart = cartRepository.save(cart);

        cart.setTotalPrice(new BigDecimal("200.00"));
        cart = cartRepository.save(cart);

        Optional<Cart> updatedCart = cartRepository.findById(cart.getId());
        assertTrue(updatedCart.isPresent());
        assertEquals(new BigDecimal("200.00"), updatedCart.get().getTotalPrice());
    }

    @Test
    public void testDeleteCart() {
        Cart cart = new Cart();
        cart.setTotalPrice(new BigDecimal("100.00"));
        cart = cartRepository.save(cart);

        cartRepository.delete(cart);

        Optional<Cart> deletedCart = cartRepository.findById(cart.getId());
        assertFalse(deletedCart.isPresent());
    }
}
