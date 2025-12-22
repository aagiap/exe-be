package com.exebe.service;

import com.exebe.dto.cart.CartDTO;
import com.exebe.dto.cart.CartItemDTO;
import com.exebe.entity.Cart;
import com.exebe.entity.CartItem;
import com.exebe.entity.Product;
import com.exebe.entity.User;
import com.exebe.mapper.CartMapper;
import com.exebe.repository.CartItemRepository;
import com.exebe.repository.CartRepository;
import com.exebe.repository.ProductRepository;
import com.exebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public CartDTO viewCart(String username) {

        User user = getUserByUsername(username);

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() ->
                        cartRepository.save(
                                Cart.builder()
                                        .user(user)
                                        .totalAmount(0.0)
                                        .build()
                        )
                );

        return CartMapper.toCartDTO(cart);
    }

    @Transactional
    public CartDTO addToCart(
            String username,
            Long productId,
            int quantity
    ) {
        User user = getUserByUsername(username);

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() ->
                        cartRepository.save(
                                Cart.builder()
                                        .user(user)
                                        .totalAmount(0.0)
                                        .build()
                        )
                );

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .build();
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);

        recalculateTotal(cart);

        return CartMapper.toCartDTO(cart);
    }


    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }


    private void recalculateTotal(Cart cart) {
        double total = cart.getCartItems().stream()
                .mapToDouble(item -> {
                    double price = item.getProduct().getDiscountedPrice() != null
                            ? item.getProduct().getDiscountedPrice()
                            : item.getProduct().getOriginalPrice();
                    return price * item.getQuantity();
                })
                .sum();

        cart.setTotalAmount(total);
    }

    @Transactional
    public CartDTO deleteItemFromCart(String username, Long productId) {

        User user = getUserByUsername(username);

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));


        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        recalculateTotal(cart);
        return CartMapper.toCartDTO(cart);
    }

    @Transactional
    public CartDTO updateQuantity(
            String username,
            Long productId,
            int quantity
    ) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not in cart"));

        if (quantity == 0) {
            cart.getCartItems().remove(cartItem);
        } else {
            cartItem.setQuantity(quantity);
        }

        recalculateTotal(cart);

        return CartMapper.toCartDTO(cart);
    }
}

