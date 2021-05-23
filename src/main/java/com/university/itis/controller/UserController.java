package com.university.itis.controller;

import com.university.itis.mapper.UserMapper;
import com.university.itis.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {
    private final UserMapper userMapper;

    @GetMapping
    ResponseEntity getProfile(ServletRequest request) {
        return ResponseEntity.ok(userMapper.toViewDto((User) request.getAttribute("user")));
    }
}
