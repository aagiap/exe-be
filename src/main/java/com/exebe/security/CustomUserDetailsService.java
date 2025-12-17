package com.exebe.security;


import com.exebe.exception.CustomException;
import com.exebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new CustomException(
                                                406,
                                                "USER_NAME_PASSWORD_NOT_MATCH",
                                                HttpStatus.NOT_ACCEPTABLE));

        var enable = user.isEnable();

        if (Objects.equals(enable, Boolean.FALSE)) {
            throw new CustomException(406, "ACCOUNT_LOCK", HttpStatus.NOT_ACCEPTABLE);
        }

        return new UserPrincipal(user);
    }
}
