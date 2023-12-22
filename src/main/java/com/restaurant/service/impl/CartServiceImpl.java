package com.restaurant.service.impl;

import com.restaurant.entity.Cart;
import com.restaurant.entity.OrderEntry;
import com.restaurant.entity.Product;
import com.restaurant.entity.User;
import com.restaurant.repository.CartRepository;
import com.restaurant.repository.OrderEntryRepository;
import com.restaurant.service.CartService;
import com.restaurant.service.ProductService;
import com.restaurant.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderEntryRepository orderEntryRepository;

    public CartServiceImpl(CartRepository cartRepository, UserService userService, ProductService productService, OrderEntryRepository orderEntryRepository) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.productService = productService;
        this.orderEntryRepository = orderEntryRepository;
    }

    @Override
    public Cart createCart(Cart cart, Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("Cart cant be created because user does not exist");
        }

        List<OrderEntry> entries = new ArrayList<>();
        for (OrderEntry orderEntry : cart.getEntries()) {
            Product product = orderEntry.getProduct();
            if (product == null) {
                throw new RuntimeException("Cart cant be created because product does not exist");
            }
            Product temp = productService.getProductById(product.getId());
            if (product == null) {
                throw new RuntimeException("Cart cant be created because product does not exist");
            }
            orderEntry.setProduct(temp);
            entries.add(orderEntry);
        }

        cart.setEntries(entries);
        cart.setCustomer(user);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart addProductToCart(Long cartId, Long productId) {
        Cart cart = getCartById(cartId);
        if (cart.getEntries() == null) {
            cart.setEntries(new ArrayList<>());
        }

        Product product = productService.getProductById(productId);

        OrderEntry entry = cart.getEntries().stream().filter(x -> productId.equals(x.getProduct().getId())).findFirst().orElse(null);
        if (entry == null) {
            cart.getEntries().add(new OrderEntry(product, 1, product.getPrice()));
            return cartRepository.save(cart);
        }
        entry.setQuantity(entry.getQuantity() + 1);
        entry.setTotalPrice(entry.getTotalPrice().add(product.getPrice()));
        cart.setTotalPrice(cart.getTotalPrice().add(product.getPrice()));
        cartRepository.save(cart);
        orderEntryRepository.save(entry);
        return getCartById(cartId);
    }

    @Override
    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }
}
