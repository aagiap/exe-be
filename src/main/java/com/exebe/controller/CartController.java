package com.exebe.controller;

import com.exebe.base.BaseController;
import com.exebe.base.BaseResponse;
import com.exebe.dto.cart.CartDTO;
import com.exebe.entity.User;
import com.exebe.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController extends BaseController {

    private final CartService cartService;


    @GetMapping
    public BaseResponse<CartDTO> viewCart(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return wrapSuccess(
                cartService.viewCart(userDetails.getUsername())
        );
    }


    @PostMapping("/add")
    public BaseResponse<CartDTO> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        return wrapSuccess(
                cartService.addToCart(
                        userDetails.getUsername(),
                        productId,
                        quantity
                )
        );
    }
}