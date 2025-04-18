// JwtUtil.java
package com.example.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private final Key key;
    private final long expireTimeMs;

    public JwtUtil(String secretKey, long expireTimeMs) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expireTimeMs = expireTimeMs;
    }

    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
    	
    	try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
        	System.out.println("JWT 유효성 실패: " + e.getMessage()); // 🔥 여기 찍어보세요
            return false;
        }
    }
}