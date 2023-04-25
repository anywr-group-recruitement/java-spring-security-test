package io.anywr.tests.java_spring_security_test.configurations.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import static io.anywr.tests.java_spring_security_test.utils.SecurityConstants.TOKEN_VALIDITY;

@Configuration
public class JwtTokenUtil implements InitializingBean {

    @Value("${app.security.jwt.secret}")
    private String jwtSecret;

    private Key secretKey;

    @Override
    public void afterPropertiesSet() {
        // Create a signing key
        if (jwtSecret == null || jwtSecret.length() == 0) {
            secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        } else {
            secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        }
    }

    public String getUsernameFromToken(@NonNull String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Claims getAllClaimsFromToken(@NonNull String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody();
    }

    @SuppressWarnings("unused")
    public <T> T getClaimFromToken(@NonNull String token, @NonNull Function<Claims, T> claimsResolver) throws JwtException {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // TODO: Pass payload instead of username.
    public String generateToken(@NonNull String username) {
        return Jwts.builder()
                .setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(secretKey)
                .compact();
    }
}
