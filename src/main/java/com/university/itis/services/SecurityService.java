package com.university.itis.services;

import com.university.itis.dto.LoginForm;
import com.university.itis.dto.RegisterForm;
import com.university.itis.model.User;
import com.university.itis.utils.Result;

public interface SecurityService {
    User getByAuthToken(String token);
    Result register(RegisterForm form);
    Result login(LoginForm form);
}
