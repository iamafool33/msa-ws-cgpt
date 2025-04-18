package com.example.auth.config;

import com.example.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expire-time-ms}")
    private long expireTimeMs;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secretKey, expireTimeMs);
    }
}
