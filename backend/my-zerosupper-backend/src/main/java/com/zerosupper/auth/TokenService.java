package com.zerosupper.auth;

import com.zerosupper.user.UserAccount;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {
    private final AuthTokenRepository tokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final Duration lifetime;

    public TokenService(AuthTokenRepository tokenRepository,
                        @Value("${app.auth.token-lifetime:PT168H}") Duration lifetime) {
        this.tokenRepository = tokenRepository;
        this.lifetime = lifetime;
    }

    @Transactional
    public IssuedToken issue(UserAccount user) {
        tokenRepository.deleteExpired(Instant.now());
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        Instant expiresAt = Instant.now().plus(lifetime);
        tokenRepository.save(new AuthToken(hash(rawToken), user, expiresAt));
        return new IssuedToken(rawToken, expiresAt);
    }

    @Transactional(readOnly = true)
    public Optional<UserAccount> authenticate(String rawToken) {
        if (rawToken == null || rawToken.isBlank()) {
            return Optional.empty();
        }
        return tokenRepository.findByTokenHashAndExpiresAtAfter(hash(rawToken), Instant.now())
                .map(AuthToken::getUser)
                .filter(UserAccount::isEnabled);
    }

    @Transactional
    public void revoke(String rawToken) {
        if (rawToken != null && !rawToken.isBlank()) {
            tokenRepository.deleteByTokenHash(hash(rawToken));
        }
    }

    private String hash(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(token.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    public record IssuedToken(String value, Instant expiresAt) {
    }
}
