package com.zerosupper.auth;

import com.zerosupper.common.ApiException;
import com.zerosupper.user.Role;
import com.zerosupper.user.UserAccount;
import com.zerosupper.user.UserRepository;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    public UserAccount register(String email, String password) {
        String normalizedEmail = normalizeEmail(email);
        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new ApiException(HttpStatus.CONFLICT, "EMAIL_EXISTS", "這個電子郵件已經註冊過。");
        }
        return userRepository.save(new UserAccount(
                normalizedEmail, passwordEncoder.encode(password), Role.USER));
    }

    @Transactional
    public LoginResult login(String email, String password) {
        UserAccount user = userRepository.findByEmailIgnoreCase(normalizeEmail(email))
                .orElseThrow(this::invalidCredentials);
        if (!user.isEnabled() || !passwordEncoder.matches(password, user.getPasswordHash())) {
            throw invalidCredentials();
        }
        TokenService.IssuedToken token = tokenService.issue(user);
        return new LoginResult(user, token);
    }

    private ApiException invalidCredentials() {
        return new ApiException(HttpStatus.UNAUTHORIZED, "INVALID_CREDENTIALS", "電子郵件或密碼不正確。");
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    public record LoginResult(UserAccount user, TokenService.IssuedToken token) {
    }
}
