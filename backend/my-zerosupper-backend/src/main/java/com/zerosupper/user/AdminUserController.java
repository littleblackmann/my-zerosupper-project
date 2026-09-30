package com.zerosupper.user;

import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final UserRepository userRepository;

    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    List<UserSummary> list() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(user -> new UserSummary(user.getId(), user.getEmail(), user.getRole(),
                        user.isEnabled(), user.getCreatedAt()))
                .toList();
    }

    public record UserSummary(Long userId, String email, Role role, boolean enabled, Instant createdAt) {
    }
}
