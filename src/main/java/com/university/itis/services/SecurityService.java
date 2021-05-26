package com.university.itis.services;

import com.university.itis.dto.LoginForm;
import com.university.itis.dto.RegisterForm;
import com.university.itis.dto.TokenDto;
import com.university.itis.dto.UserDto;
import com.university.itis.model.User;

public interface SecurityService {
    User getByAuthToken(String token);
    UserDto register(RegisterForm form);
    TokenDto login(LoginForm form);
}
