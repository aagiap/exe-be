package com.exebe.entity;

import com.exebe.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    // --- LIÊN KẾT USER ---
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
    // Nếu Guest mua -> user = null.
    // Nếu User mua -> user = user_id.

    // --- THÔNG TIN NGƯỜI NHẬN (Form điền vào đây) ---
    // Phải lưu riêng, không phụ thuộc vào bảng User để tránh user đổi info làm sai lịch sử đơn
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private String shippingAddress;
    private String note; // Ghi chú của khách

    // --- TRẠNG THÁI & THANH TOÁN ---
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // PENDING, SHIPPING...

    private Double totalPrice; // Tổng tiền đơn hàng

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = OrderStatus.PENDING;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
