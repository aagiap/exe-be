package com.exebe.constant;

import lombok.Getter;

@Getter
public enum CategoryType {
    FASHION("Thời trang"),    // Thời trang
    FURNITURE("Nội thất"),  // Nội thất
    HOUSEHOLD("Gia dụng"),  // Gia dụng
    DECOR("Decol")     ;  // Decor

    private final String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }
}
