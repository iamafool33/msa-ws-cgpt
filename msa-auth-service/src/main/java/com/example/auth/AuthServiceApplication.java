package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.auth.config.JwtProperties;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class) // 설정 클래스 등록
public class AuthServiceApplication {

    private final JwtProperties jwtProperties;

    public AuthServiceApplication(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @PostConstruct
    public void printJwtConfig() {
        System.out.println("✅ JWT Secret: " + jwtProperties.getSecret());
        System.out.println("✅ JWT Expire(ms): " + jwtProperties.getExpire());
    }
}
