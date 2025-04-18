package com.example.auth.controller;

import com.example.auth.domain.User;
import com.example.auth.mapper.UserMapper;
import com.example.auth.service.UserService;
import com.example.common.dto.LoginRequestDto;
import com.example.common.dto.TokenResponseDto;
import com.example.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    public UserController(UserService userService, JwtUtil jwtUtil, UserMapper userMapper) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody LoginRequestDto dto) {
        try {
            userService.signup(dto.getUsername(), dto.getPassword());
            return ResponseEntity.ok("회원가입 성공");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {
        try {
            TokenResponseDto token = userService.login(dto);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("로그인 실패: " + e.getMessage());
        }
    }

    /**
     * JWT 토큰 기반 사용자 정보 조회
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Authorization 헤더가 필요합니다");
        }

        String token = authHeader.replace("Bearer ", "");

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(401).body("유효하지 않은 토큰입니다");
        }

        String username = jwtUtil.getUsername(token);
        User user = userMapper.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(404).body("해당 사용자를 찾을 수 없습니다");
        }

        user.setPassword(null); // 비밀번호 숨기기
        return ResponseEntity.ok(user);
    }
}
