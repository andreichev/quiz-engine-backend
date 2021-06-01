package com.university.itis.services;

import com.university.itis.dto.ImageDto;
import com.university.itis.dto.TokenDto;
import com.university.itis.dto.UploadImageDto;
import com.university.itis.dto.authorization.LoginForm;
import com.university.itis.dto.authorization.RegisterForm;
import com.university.itis.model.User;

public interface UserService {
    User getByAuthToken(String token);
    TokenDto register(RegisterForm form);
    TokenDto login(LoginForm form);
    ImageDto updateAvatar(User user, UploadImageDto uploadImageDto);
    void deleteAvatar(User user);
}
