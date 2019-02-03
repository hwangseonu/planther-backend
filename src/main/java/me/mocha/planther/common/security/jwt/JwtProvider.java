package me.mocha.planther.common.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.access.exp}")
    private long accessExp;

    @Value("${jwt.refresh.exp}")
    private long refreshExp;

    private String secret = System.getenv("JWT_SECRET");

    public JwtProvider() {
        if (!StringUtils.hasText(secret)) secret = "planther-secret";
    }

    public String generateToken(String username, JwtType type) {
        return Jwts.builder()
                .setSubject(type.toString())
                .setExpiration(new Date(System.currentTimeMillis() + (type == JwtType.ACCESS ? accessExp : refreshExp)))
                .setIssuedAt(new Date())
                .setNotBefore(new Date())
                .setId(UUID.randomUUID().toString())
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().get("username").toString();
    }

    public LocalDateTime getExpiration(String token) {
        return LocalDateTime.ofInstant(Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .toInstant(), ZoneId.systemDefault());
    }

    public boolean validToken(String token, JwtType type) {
        try {
            Jwts.parser().requireSubject(type.toString()).setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.error("jwt error - {}", e.getMessage());
            return false;
        }
    }

}
