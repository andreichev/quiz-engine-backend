package com.university.itis.controller;

import com.university.itis.config.filter.JwtHelper;
import com.university.itis.dto.LoginForm;
import com.university.itis.dto.RegisterForm;
import com.university.itis.dto.TokenDto;
import com.university.itis.dto.UserDto;
import com.university.itis.model.Role;
import com.university.itis.model.User;
import com.university.itis.services.UserService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.ResponseCreator;
import com.university.itis.utils.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Optional;

@Controller
class AuthenticationController extends ResponseCreator {

    private final JwtHelper jwtHelper;
    private final UserService userService;
    private final Validator validator;

    public AuthenticationController(JwtHelper jwtHelper, UserService userService, Validator validator) {
        this.jwtHelper = jwtHelper;
        this.userService = userService;
        this.validator = validator;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity registerUser(@RequestBody RegisterForm registerForm) {
        Optional<ErrorEntity> formErrorOrNull = validator.getUserRegisterFormError(registerForm);
        if (formErrorOrNull.isPresent()) {
            return createErrorResponse(formErrorOrNull.get());
        }
        User user = User.builder()
                .email(registerForm.getEmail())
                .password(registerForm.getPassword())
                .phone(registerForm.getPhone())
                .roles(Collections.singleton(Role.ROLE_USER))
                .isActive(true)
                .isEmailConfirmed(false)
                .build();
        UserDto userDto = userService.save(user);
        return createGoodResponse(userDto);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody LoginForm loginForm) {
        Optional<ErrorEntity> formErrorOrNull = validator.getLoginFormError(loginForm);
        if (formErrorOrNull.isPresent()) {
            return createErrorResponse(formErrorOrNull.get());
        }
        User user = userService.getByEmail(loginForm.getEmail());
        return createGoodResponse(new TokenDto(jwtHelper.generateToken(user)));
    }
}
