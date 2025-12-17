package com.exebe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double originalPrice; // Giá gốc

    private Double discountedPrice; // Giá sau khi giảm

    private String thumbnail; // Ảnh đại diện

    @ElementCollection
    private List<String> galleryImages; // List ảnh chi tiết

    private boolean isFeatured; // Sản phẩm nổi bật (cho trang chủ)
    private boolean isActive; // Ẩn/Hiện sản phẩm

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
