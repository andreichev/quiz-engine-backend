package com.university.itis.controller;

import com.university.itis.dto.LoginForm;
import com.university.itis.dto.RegisterForm;
import com.university.itis.dto.TokenDto;
import com.university.itis.dto.UserDto;
import com.university.itis.services.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
class AuthenticationController {
    private final SecurityService securityService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public UserDto registerUser(@RequestBody RegisterForm registerForm) {
        return securityService.register(registerForm);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public TokenDto auth(@RequestBody LoginForm loginForm) {
        return securityService.login(loginForm);
    }
}
