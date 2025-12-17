package com.exebe.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "order_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    // --- SNAPSHOT GIÁ ---
    // Cực kỳ quan trọng: Lưu giá tại thời điểm mua.
    // Nếu sau này Admin sửa giá Product thì đơn hàng cũ không bị sai tiền.
    private Double priceAtPurchase;
}
