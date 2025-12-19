package com.exebe.mapper;

import com.exebe.dto.product.ProductDTO;
import com.exebe.entity.Product;

public class ProductMapper {

    public static ProductDTO toProductDTO(Product product) {

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .originalPrice(product.getOriginalPrice())
                .discountedPrice(product.getDiscountedPrice())
                .thumbnail(product.getThumbnail())
                .galleryImages(product.getGalleryImages())
                .isFeatured(product.isFeatured())
                .isActive(product.isActive())
                .categoryId(
                        product.getCategory() != null
                                ? product.getCategory().getId()
                                : null
                )
                .categoryName(
                        product.getCategory() != null
                                ? product.getCategory().getName()
                                : null
                )
                .build();
    }
}
