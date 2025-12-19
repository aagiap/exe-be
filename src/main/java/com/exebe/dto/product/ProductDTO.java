package com.exebe.dto.product;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private Double originalPrice;
    private Double discountedPrice;

    private String thumbnail;
    private List<String> galleryImages;

    private boolean isFeatured;
    private boolean isActive;

    private Long categoryId;
    private String categoryName;
}