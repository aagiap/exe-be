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
                    Product p = item.getProduct();
                    double price = p.getDiscountedPrice() != null
                            ? p.getDiscountedPrice()
                            : p.getOriginalPrice();
                    return price * item.getQuantity();
                })
                .sum();

        cart.setTotalAmount(total);
        cartRepository.save(cart);
    }
}

