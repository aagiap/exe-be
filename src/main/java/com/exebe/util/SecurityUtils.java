package com.exebe.util;


import com.exebe.constant.UserRole;
import com.exebe.security.UserPrincipal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {
    public static Optional<com.exebe.security.UserPrincipal> requester() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication && null != authentication.getDetails()) {
            Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return object instanceof UserPrincipal
                    ? Optional.of((UserPrincipal) object)
                    : Optional.empty();
        } else {
            return Optional.empty();
        }
    }

    public static boolean checkRole(UserRole role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getAuthorities() == null) {
            return false;
        }

        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals(role.toString()));
    }
}
