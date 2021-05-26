package com.university.itis.controller;

import com.university.itis.dto.ImageDto;
import com.university.itis.dto.UploadImageDto;
import com.university.itis.mapper.UserMapper;
import com.university.itis.model.User;
import com.university.itis.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping
    ResponseEntity getProfile(ServletRequest request) {
        return ResponseEntity.ok(userMapper.toViewDto((User) request.getAttribute("user")));
    }

    @PostMapping(value = "/avatar")
    ImageDto updateAvatar(ServletRequest request, @ModelAttribute UploadImageDto uploadImageDto) {
        User user = (User) request.getAttribute("user");
        return userService.updateAvatar(user, uploadImageDto);
    }

    @DeleteMapping(value = "/avatar")
    ResponseEntity deleteAvatar(ServletRequest request) {
        User user = (User) request.getAttribute("user");
        userService.deleteAvatar(user);
        return ResponseEntity.ok().build();
    }
}
