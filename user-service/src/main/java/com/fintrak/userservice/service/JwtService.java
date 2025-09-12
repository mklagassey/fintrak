package com.fintrak.userservice.service;

import com.fintrak.userservice.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    private JwtParser getJwtParser() {
        return Jwts.parser().verifyWith(getSigningKey()).build();
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = getJwtParser().parseSignedClaims(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            // Token is invalid for any reason (expired, malformed, etc.)
            return false;
        }
    }

    public UUID extractUserId(String token) {
        Jws<Claims> claims = getJwtParser().parseSignedClaims(token);
        String userIdStr = claims.getPayload().getSubject();
        return UUID.fromString(userIdStr);
    }
}