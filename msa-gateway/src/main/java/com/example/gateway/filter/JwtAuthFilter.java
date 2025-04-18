// JwtAuthFilter.java
package com.example.gateway.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.common.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        
        System.out.println("=== JwtAuthFilter ===");
        System.out.println("URI: " + request.getRequestURI());
        System.out.println("Authorization Header: " + authHeader);
        System.out.println("Token valid: " + jwtUtil.validateToken("Bearer " + authHeader));
        

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided");
            return;
        }

        String token = authHeader.replace("Bearer ", "").trim();
        System.out.println("1. Authorization Header:" + authHeader);
        System.out.println("2. Token valid:" + jwtUtil.validateToken(token));
        System.out.println("3. token:" + token);
        
        
        if (!jwtUtil.validateToken(token)) {
            
        	
        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(request, response);
    }
    
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return request.getMethod().equalsIgnoreCase("OPTIONS") // ✅ 이 줄 추가!
               || path.startsWith("/api/user/signup") 
               || path.startsWith("/api/user/login") 
        	   || path.startsWith("/api/board")
        		;
    }
    
}