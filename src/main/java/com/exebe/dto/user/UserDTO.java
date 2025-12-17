package com.exebe.dto.user;


import com.exebe.constant.UserRole;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTO {
    private Long id;

    private String username;

    private String fullName;

    private UserRole role;

    private String email;

    private boolean isEnable;
}
