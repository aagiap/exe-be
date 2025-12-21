package com.exebe.dto.cart;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {

    private Long cartItemId;

    private Long productId;
    private String productName;
    private String thumbnail;

    private Double price;
    private Integer quantity;

    private Double subTotal;
}
