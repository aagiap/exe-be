package com.exebe.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponseDTO {
    private String username;
    private String fullName;
    private String email;
    private boolean isEnable;
    private String role;
}
