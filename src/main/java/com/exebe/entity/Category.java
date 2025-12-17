package com.exebe.entity;

import com.exebe.constant.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name; // Ví dụ: "Nội thất mây tre"

    @Enumerated(EnumType.STRING)
    private CategoryType type; // Dùng để filter trên Nav Bar

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
