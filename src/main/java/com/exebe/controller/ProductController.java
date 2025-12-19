package com.exebe.controller;

import com.exebe.base.BaseController;
import com.exebe.base.BaseResponse;
import com.exebe.base.PageDTO;
import com.exebe.dto.product.ProductDTO;
import com.exebe.entity.Product;
import com.exebe.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {

    private final ProductService productService;

    @GetMapping("/view")
    public BaseResponse<PageDTO<ProductDTO>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return wrapSuccess(
                productService.search(keyword, categoryId, minPrice, maxPrice, page, size
                )
        );
    }

    @GetMapping("/view/{id}")
    public BaseResponse<ProductDTO> viewProductDetail(@PathVariable Long id) {
        return wrapSuccess(productService.viewProductDetail(id));
    }
}
