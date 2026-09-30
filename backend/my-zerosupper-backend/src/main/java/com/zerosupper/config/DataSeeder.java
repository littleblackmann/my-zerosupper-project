package com.zerosupper.config;

import com.zerosupper.product.Product;
import com.zerosupper.product.ProductCategory;
import com.zerosupper.product.ProductRepository;
import com.zerosupper.user.Role;
import com.zerosupper.user.UserAccount;
import com.zerosupper.user.UserRepository;
import java.math.BigDecimal;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataSeeder implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;

    public DataSeeder(UserRepository userRepository, ProductRepository productRepository,
                      PasswordEncoder passwordEncoder,
                      @Value("${app.admin.email:}") String adminEmail,
                      @Value("${app.admin.password:}") String adminPassword) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    @Transactional
    public void run(String... args) {
        seedAdmin();
        seedProducts();
    }

    private void seedAdmin() {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            log.warn("No admin was seeded. Set APP_ADMIN_EMAIL and APP_ADMIN_PASSWORD before startup.");
            return;
        }
        String normalizedEmail = adminEmail.trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            return;
        }
        userRepository.save(new UserAccount(normalizedEmail, passwordEncoder.encode(adminPassword), Role.ADMIN));
        log.info("Created ZERO Supper administrator account for {}", normalizedEmail);
    }

    private void seedProducts() {
        if (productRepository.count() > 0) {
            return;
        }
        productRepository.save(new Product("經典牛肉堡", ProductCategory.BURGER,
                new BigDecimal("180.00"), 30, "牛肉、起司、生菜與 ZERO Supper 經典醬汁。",
                "/products/classic-burger.svg"));
        productRepository.save(new Product("經典牛肉堡套餐", ProductCategory.FOOD,
                new BigDecimal("250.00"), 30, "經典牛肉堡搭配脆薯與飲品。",
                "/products/burger-meal.svg"));
        productRepository.save(new Product("ZERO Supper 菜單", ProductCategory.MENU,
                new BigDecimal("1.00"), 1, "紀念版菜單封面。",
                "/products/menu-card.svg"));
    }
}
