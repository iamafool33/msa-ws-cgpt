package com.example.auth.mapper;

import com.example.auth.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    void insertUser(User user);
}