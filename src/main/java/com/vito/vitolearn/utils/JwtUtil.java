package com.vito.vitolearn.utils;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.*;
public class JwtUtil {
    private static final String SECRET = "your-secret-key";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    
    public static String generateToken(String username, String userId, String role) {
        return Jwts.builder()
            .claim("userId", userId)
            .claim("role", role)
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }
    
    public static String extractUsername(String token) {
        return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    public static Map<String, Object> extractClaims(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token)
            .getBody();
            
        return claims;
    }
    public static boolean validateToken(String token) {
        try {
            // Parse the token with the provided secret key
            Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token);
    
            // Check if the token is not expired
            Date now = new Date();
            Date expiration = claims.getBody().getExpiration();
            return !expiration.before(now); // Token is valid if expiration date is in the future
        } catch (JwtException | IllegalArgumentException e) {
            // If there's an exception parsing the token or it's not valid
            // You might log the exception or handle it as per your application's requirements
            return false; // Token is invalid
        }
    }
    
    
}
