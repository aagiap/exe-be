package com.exebe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Nationalized;

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

    @Nationalized
    @Size(max = 1000)
    @Column(name = "name", length = 1000)
    private String name;

    @Nationalized
    @Size(max = 1000)
    @Column(name = "description", length = 1000)
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
