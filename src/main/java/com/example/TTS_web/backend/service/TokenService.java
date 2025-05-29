package com.example.TTS_web.backend.service;

import com.example.TTS_web.backend.entity.TrainingToken;
import com.example.TTS_web.backend.repository.TokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    public String generateToken(int expirationHours, Long userId, String baseModelName) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusHours(expirationHours);
        String token = Jwts.builder()
                .setSubject("tts-training-token")
                .claim("userId", userId)
                .claim("baseModel", baseModelName)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        TrainingToken trainingToken = new TrainingToken();
        trainingToken.setToken(token);
        trainingToken.setCreatedAt(now);
        trainingToken.setExpiresAt(expiration);
        trainingToken.setUsed(false);

        tokenRepository.save(trainingToken);

        return token;
    }

    public Map<String, Object> getDataFromToken(String token) throws JwtException {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Map<String, Object> result = new HashMap<>();
        result.put("userId", claims.get("userId", Long.class));
        result.put("baseModel", claims.get("baseModel", String.class));
        result.put("issuedAt", claims.getIssuedAt());
        result.put("expiration", claims.getExpiration());

        return result;
    }

    public Map<String, Object> parseToken(String token) throws JwtException {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Claims claims = jws.getBody();

        // Проверка срока действия
        Date expiration = claims.getExpiration();
        if (expiration != null && new Date().after(expiration)) {
            throw new JwtException("Токен истек");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("userId", claims.get("userId", Long.class));
        result.put("baseModel", claims.get("baseModel", String.class));
        result.put("issuedAt", claims.getIssuedAt());
        result.put("expiration", expiration);

        return result;
    }

    public Optional<TrainingToken> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public boolean validateToken(String token) {
        try {
            // Парсим заголовки токена, чтобы проверить срок действия
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            // Если дошло сюда — токен валиден по подписи и сроку действия
            return getToken(token)
                    .filter(t -> !t.isUsed())
                    .map(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                    .orElse(false);

        } catch (JwtException e) {
            // Токен невалиден или истёк
            return false;
        }
    }

    public void markAsUsed(String token) {
        getToken(token).ifPresent(t -> {
            t.setUsed(true);
            tokenRepository.save(t);
        });
    }
}
