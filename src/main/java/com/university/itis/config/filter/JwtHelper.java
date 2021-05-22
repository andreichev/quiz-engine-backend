package com.university.itis.config.filter;

import com.university.itis.model.User;
import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@Log
public class JwtHelper {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("email", user.getEmail());
        claims.put("password", user.getPassword());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.severe("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.severe("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.severe("Malformed jwt");
        } catch (SignatureException sEx) {
            log.severe("Invalid signature");
        } catch (Exception e) {
            log.severe("invalid token");
        }
        return false;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles()
                .stream()
                .map( (role) -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return (String) claims.get("email");
    }

    public String getPasswordFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return (String) claims.get("password");
    }
}