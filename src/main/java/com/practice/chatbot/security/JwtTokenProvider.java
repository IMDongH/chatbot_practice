package com.practice.chatbot.security;

import com.practice.chatbot.security.vo.UserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessExpiration;
    private final long refreshExpiration;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
        @Value("${jwt.access-expiration}") long accessExpiration,
        @Value("${jwt.refresh-expiration}") long refreshExpiration) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

    public String generateAccessToken(String id, String name, String email, String role) {
        return generateToken(id, name, email, role, accessExpiration);
    }

    public String generateRefreshToken(String id) {
        return generateToken(id, null, null, null, refreshExpiration);
    }

    private String generateToken(String id, String name, String email, String role, long expirationTime) {
        JwtBuilder builder = Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(key, SignatureAlgorithm.HS256);

        builder.claim("id", id);
        builder.claim("role", role);
        builder.claim("name", name);

        return builder.compact();
    }

    public Claims extractClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public UserInfo extractUserInfo(String token) {
        Claims claims = extractClaims(token);

        return UserInfo.builder()
            .id(claims.get("id", String.class))
            .name(claims.get("name", String.class))
            .email(claims.getSubject()) //
            .role(claims.get("role", String.class))
            .build();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}