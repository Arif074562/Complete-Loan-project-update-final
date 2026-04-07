package com.cts.auth.config;

import com.cts.auth.entity.User;
import com.cts.auth.enums.UserRole;
import com.cts.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Users already exist, skipping seed.");
            return;
        }
        log.info("Seeding default users...");

        userRepository.save(User.builder()
                .name("Admin User")
                .email("admin@cts.com")
                .password(passwordEncoder.encode("Admin@123"))
                .role(UserRole.ADMIN)
                .phone("+911234567890")
                .isActive(true)
                .build());

        userRepository.save(User.builder()
                .name("Loan Officer")
                .email("officer@cts.com")
                .password(passwordEncoder.encode("Test@123"))
                .role(UserRole.OFFICER)
                .phone("+911234567891")
                .isActive(true)
                .build());

        userRepository.save(User.builder()
                .name("Underwriter")
                .email("underwriter@cts.com")
                .password(passwordEncoder.encode("Test@123"))
                .role(UserRole.UNDERWRITER)
                .phone("+911234567892")
                .isActive(true)
                .build());

        userRepository.save(User.builder()
                .name("Operations")
                .email("operations@cts.com")
                .password(passwordEncoder.encode("Test@123"))
                .role(UserRole.OPERATIONS)
                .phone("+911234567893")
                .isActive(true)
                .build());

        userRepository.save(User.builder()
                .name("Collections")
                .email("collections@cts.com")
                .password(passwordEncoder.encode("Test@123"))
                .role(UserRole.COLLECTIONS)
                .phone("+911234567894")
                .isActive(true)
                .build());

        userRepository.save(User.builder()
                .name("Customer")
                .email("customer@cts.com")
                .password(passwordEncoder.encode("Test@123"))
                .role(UserRole.CUSTOMER)
                .phone("+911234567895")
                .isActive(true)
                .build());

        log.info("Default users seeded successfully.");
    }
}
