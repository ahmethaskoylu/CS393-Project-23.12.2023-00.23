package com.restaurant;

import com.restaurant.entity.Cart;
import com.restaurant.entity.Product;
import com.restaurant.entity.User;
import com.restaurant.repository.CartRepository;
import com.restaurant.repository.OrderEntryRepository;
import com.restaurant.service.CartService;
import com.restaurant.service.ProductService;
import com.restaurant.service.UserService;
import com.restaurant.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @Mock
    private OrderEntryRepository orderEntryRepository;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cartService = new CartServiceImpl(cartRepository, userService, productService, orderEntryRepository);
    }

    @Test
    void createCartTest() {
        Cart cart = new Cart();
        Long userId = 1L;
        User user = new User();
        when(userService.getUserById(userId)).thenReturn(user);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart createdCart = cartService.createCart(cart, userId);

        assertNotNull(createdCart);
        assertEquals(user, createdCart.getCustomer());
    }

    @Test
    void createCartWithNonExistentUserTest() {
        Cart cart = new Cart();
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> cartService.createCart(cart, userId));
    }

    @Test
    void getCartByIdTest() {
        Long cartId = 1L;
        Cart mockCart = new Cart();
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(mockCart));

        Cart foundCart = cartService.getCartById(cartId);

        assertNotNull(foundCart);
    }

    @Test
    void getCartByIdNotFoundTest() {
        Long cartId = 1L;
        when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> cartService.getCartById(cartId));
    }

    @Test
    void getAllCartsTest() {
        Cart cartOne = new Cart();
        Cart cartTwo = new Cart();
        List<Cart> mockCarts = Arrays.asList(cartOne, cartTwo);
        when(cartRepository.findAll()).thenReturn(mockCarts);

        List<Cart> carts = cartService.getAllCarts();

        assertEquals(2, carts.size());
    }

    @Test
    void addProductToCartTest() {
        Long cartId = 1L;
        Long productId = 2L;
        Cart cart = new Cart();
        Product product = new Product();
        cart.setEntries(new ArrayList<>());
        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
        when(productService.getProductById(productId)).thenReturn(product);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.addProductToCart(cartId, productId);

        assertNotNull(updatedCart);
    }

    @Test
    void deleteCartTest() {
        Cart cart = new Cart();

        doNothing().when(cartRepository).delete(any(Cart.class));

        cartService.deleteCart(cart);

        verify(cartRepository, times(1)).delete(cart);
    }
}
