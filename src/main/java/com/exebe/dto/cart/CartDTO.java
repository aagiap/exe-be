package com.exebe.dto.cart;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private Long cartId;

    private Long userId;

    private List<CartItemDTO> items;

    private Double totalAmount;
}
