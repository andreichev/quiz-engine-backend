package com.university.itis.services;

import com.university.itis.dto.*;
import com.university.itis.model.User;

public interface UserService {
    User getByAuthToken(String token);
    TokenDto register(RegisterForm form);
    TokenDto login(LoginForm form);
    ImageDto updateAvatar(User user, UploadImageDto uploadImageDto);
    void deleteAvatar(User user);
}
