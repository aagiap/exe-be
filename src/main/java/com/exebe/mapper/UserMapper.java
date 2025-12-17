package com.exebe.mapper;


import com.exebe.dto.user.UserDTO;
import com.exebe.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserDTO toUserDTO(User user) {
        if (Objects.isNull(user)) return null;

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .isEnable(user.isEnable())
                .build();
    }

    public static User toUser(UserDTO user) {
        if (Objects.isNull(user)) return null;

        return User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .isEnable(user.isEnable())
                .build();
    }
}
