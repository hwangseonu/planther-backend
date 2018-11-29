package me.mocha.calendar.security.jwt;

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
public class JwtTokenProvider {

    private String secret = System.getenv("JWT_SECRET");

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    public JwtTokenProvider() {
        if (!StringUtils.hasText(secret)) secret = "my-secret";
    }

    public String generateToken(String username, JwtType type) {
        return Jwts.builder()
                .setSubject(type.toString())
                .setExpiration(new Date(System.currentTimeMillis() + (type == JwtType.ACCESS ? accessExpiration : refreshExpiration)))
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
        return LocalDateTime.ofInstant(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration().toInstant(), ZoneId.systemDefault());
    }

    public boolean validToken(String token, JwtType type) {
        try {
            Jwts.parser().requireSubject(type.toString()).setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

}
