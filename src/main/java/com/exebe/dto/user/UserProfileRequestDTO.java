package com.exebe.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileRequestDTO {

    @NotBlank(message = "type is required")
    private String type; // "BASIC" or "ADDRESS"

    private String fullName;

    private String phone;

    private String address;
}
