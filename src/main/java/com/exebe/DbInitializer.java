package com.exebe;

import com.exebe.constant.UserRole;
import com.exebe.entity.User;
import com.exebe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DbInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .fullName("Admin")
                    .role(UserRole.ROLE_ADMIN)
                    .isEnable(true)
                    .build();

            userRepository.save(user);
        }
    }
}
