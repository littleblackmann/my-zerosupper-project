package com.zerosupper.auth;

import com.zerosupper.security.CurrentUser;
import com.zerosupper.user.Role;
import com.zerosupper.user.UserAccount;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    public AuthController(AuthService authService, TokenService tokenService) {
        this.authService = authService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserAccount user = authService.register(request.email(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UserResponse(user.getId(), user.getEmail(), user.getRole(), user.getCreatedAt()));
    }

    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest request) {
        AuthService.LoginResult result = authService.login(request.email(), request.password());
        UserAccount user = result.user();
        return new AuthResponse(result.token().value(), result.token().expiresAt(),
                user.getId(), user.getEmail(), user.getRole());
    }

    @PostMapping("/logout")
    ResponseEntity<Void> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String header) {
        if (header != null && header.startsWith("Bearer ")) {
            tokenService.revoke(header.substring(7));
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    UserResponse me(@AuthenticationPrincipal CurrentUser user) {
        return new UserResponse(user.id(), user.email(), user.role(), null);
    }

    public record RegisterRequest(
            @NotBlank @Email @Size(max = 320) String email,
            @NotBlank @Size(min = 10, max = 128) String password) {
    }

    public record LoginRequest(
            @NotBlank @Size(max = 320) String email,
            @NotBlank @Size(max = 128) String password) {
    }

    public record AuthResponse(String token, Instant expiresAt, Long userId, String email, Role role) {
    }

    public record UserResponse(Long userId, String email, Role role, Instant createdAt) {
    }
}
