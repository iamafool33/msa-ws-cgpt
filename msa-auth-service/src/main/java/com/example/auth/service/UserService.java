package com.example.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.domain.User;
import com.example.auth.mapper.UserMapper;
import com.example.common.dto.LoginRequestDto;


import com.example.common.dto.TokenResponseDto;
import com.example.common.util.JwtUtil;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public void signup(String username, String rawPassword) {
        if (userMapper.findByUsername(username) != null) {
            throw new RuntimeException("이미 존재하는 사용자입니다");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword)); // 🔐 암호화
        userMapper.insertUser(user);
    }

    public TokenResponseDto login(LoginRequestDto dto) {
        User user = userMapper.findByUsername(dto.getUsername());
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("아이디 또는 비밀번호가 틀립니다");
        }

        String token = jwtUtil.createToken(user.getUsername());
        return new TokenResponseDto(token, user.getUsername());
    }
}
