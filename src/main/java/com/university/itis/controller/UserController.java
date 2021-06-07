package com.university.itis.controller;

import com.university.itis.dto.ImageDto;
import com.university.itis.dto.UploadImageDto;
import com.university.itis.dto.quiz_passing.QuizPassingShortDto;
import com.university.itis.dto.user.UserDto;
import com.university.itis.mapper.UserMapper;
import com.university.itis.model.User;
import com.university.itis.services.QuizPassingService;
import com.university.itis.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
@AllArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final QuizPassingService quizPassingService;
    private final UserService userService;

    @GetMapping
    UserDto getProfile(ServletRequest request) {
        return userMapper.toViewDto((User) request.getAttribute("user"));
    }

    @GetMapping(value = "/history")
    List<QuizPassingShortDto> history(ServletRequest request) {
        User user = (User) request.getAttribute("user");
        return quizPassingService.getHistory(user);
    }

    @PostMapping(value = "/avatar")
    ImageDto updateAvatar(ServletRequest request, @ModelAttribute UploadImageDto uploadImageDto) {
        User user = (User) request.getAttribute("user");
        return userService.updateAvatar(user, uploadImageDto);
    }

    @DeleteMapping(value = "/avatar")
    void deleteAvatar(ServletRequest request) {
        User user = (User) request.getAttribute("user");
        userService.deleteAvatar(user);
    }
}
