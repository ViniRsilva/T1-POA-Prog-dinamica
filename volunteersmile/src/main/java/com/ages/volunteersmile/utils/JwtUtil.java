package com.ages.volunteersmile.utils;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.domain.volunteer.model.Volunteer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final long EXPIRATION = 1000 * 60 * 60 * 24;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(Volunteer volunteer) {
        return Jwts.builder()
                .setSubject(volunteer.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(VolunteerDTO volunteerDTO) {
        return Jwts.builder()
                .setSubject(volunteerDTO.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}