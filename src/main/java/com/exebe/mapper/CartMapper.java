package com.exebe.mapper;

import com.exebe.dto.cart.CartDTO;
import com.exebe.dto.cart.CartItemDTO;
import com.exebe.entity.Cart;
import com.exebe.entity.CartItem;
import com.exebe.entity.Product;

import java.util.List;

public class CartMapper {


    public static CartDTO toCartDTO(Cart cart) {

        if (cart == null) {
            return null;
        }

        List<CartItemDTO> items = cart.getCartItems() == null
                ? List.of()
                : cart.getCartItems().stream()
                .map(CartMapper::toCartItemDTO)
                .toList();

        return CartDTO.builder()
                .cartId(cart.getId())
                .userId(cart.getUser().getId())
                .items(items)
                .totalAmount(cart.getTotalAmount())
                .build();
    }

    public static CartItemDTO toCartItemDTO(CartItem item) {

        Product product = item.getProduct();

        double price = product.getDiscountedPrice() != null
                ? product.getDiscountedPrice()
                : product.getOriginalPrice();

        return CartItemDTO.builder()
                .cartItemId(item.getId())
                .productId(product.getId())
                .productName(product.getName())
                .thumbnail(product.getThumbnail())
                .price(price)
                .quantity(item.getQuantity())
                .subTotal(price * item.getQuantity())
                .build();
    }
}
