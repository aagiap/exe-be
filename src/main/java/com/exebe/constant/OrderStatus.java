package com.exebe.constant;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("Chờ xác nhận"),    // Chờ xác nhận (khi mới submit form)
    SHIPPING("Đang giao hàng"),   // Chờ giao hàng/Đang giao
    DELIVERED("Đã giao thành công"),  // Đã giao thành công
    CANCELLED("Đã hủy")   // Đã hủy
    ;

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }
}
