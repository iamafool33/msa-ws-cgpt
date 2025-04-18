package com.example.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;

@RestController
@RequestMapping("/api")
public class RoutingController {

    @Value("${routes.auth-service}")
    private String authServiceUrl;

    @Value("${routes.board-service}")
    private String boardServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/user/**")
    public ResponseEntity<?> forwardUser(HttpServletRequest request, @RequestBody(required = false) String body) {
        return forwardRequest(request, authServiceUrl, HttpMethod.POST, body);
    }

    @RequestMapping(value = "/board/**", method = {
            RequestMethod.GET,
            RequestMethod.POST,
            RequestMethod.PUT,
            RequestMethod.DELETE
    })
    public ResponseEntity<?> forwardBoard(HttpServletRequest request,
                                          @RequestBody(required = false) String body) {
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        return forwardRequest(request, boardServiceUrl, method, body);
    }

    private ResponseEntity<?> forwardRequest(HttpServletRequest request, String targetService, HttpMethod method, String body) {
        String targetUrl = targetService + request.getRequestURI().replace("/api", "");

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!headerName.equalsIgnoreCase("authorization")) {
                headers.set(headerName, request.getHeader(headerName));
            }
        }

        // Authorization 헤더는 따로 처리
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            headers.set("Authorization", authHeader);
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(targetUrl, method, entity, String.class);
    }
}
