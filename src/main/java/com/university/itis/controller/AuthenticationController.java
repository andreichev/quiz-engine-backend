package com.university.itis.controller;

import com.university.itis.dto.LoginForm;
import com.university.itis.dto.RegisterForm;
import com.university.itis.services.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
class AuthenticationController {
    private final SecurityService securityService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity registerUser(@RequestBody RegisterForm registerForm) {
        return securityService.register(registerForm).getResponseEntity();
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody LoginForm loginForm) {
        return securityService.login(loginForm).getResponseEntity();
    }
}
