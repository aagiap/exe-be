package com.exebe.controller;

import com.exebe.base.BaseController;
import com.exebe.base.BaseResponse;
import com.exebe.dto.product.CategoryDTO;
import com.exebe.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    private final CategoryService categoryService;

    @GetMapping("")
    public BaseResponse<List<CategoryDTO>> getAll() {
        return wrapSuccess(categoryService.getAll());
    }
}
