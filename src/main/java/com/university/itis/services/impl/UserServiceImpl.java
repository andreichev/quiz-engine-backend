package com.university.itis.services.impl;

import com.university.itis.config.filter.JwtHelper;
import com.university.itis.dto.*;
import com.university.itis.exceptions.InvalidTokenException;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.exceptions.ValidationException;
import com.university.itis.mapper.ImageMapper;
import com.university.itis.mapper.UserMapper;
import com.university.itis.model.Image;
import com.university.itis.model.Role;
import com.university.itis.model.User;
import com.university.itis.repository.ImageRepository;
import com.university.itis.repository.UserRepository;
import com.university.itis.services.ImageFileService;
import com.university.itis.services.UserService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final Validator validator;
    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageRepository imageRepository;
    private final ImageFileService imageFileService;
    private final ImageMapper imageMapper;

    @Override
    public User getByAuthToken(String token) {
        if (token == null || jwtHelper.validateToken(token) == false) {
            throw new InvalidTokenException("Invalid token or token header not found");
        }
        String email = jwtHelper.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            String tokenPassword = jwtHelper.getPasswordFromToken(token);
            if(tokenPassword.equals(user.getPassword()) == false) {
                throw new InvalidTokenException("Wrong password");
            }
            return optionalUser.get();
        } else {
            throw new NotFoundException("User with email " + email + " not found");
        }
    }

    @Override
    public TokenDto login(LoginForm form) {
        Optional<ErrorEntity> formErrorOrNull = validator.getLoginFormError(form);
        if (formErrorOrNull.isPresent()) {
            throw new ValidationException(formErrorOrNull.get());
        }
        User user = userRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new NotFoundException("User with email " + form.getEmail() + " not found"));
        return new TokenDto(jwtHelper.generateToken(user));
    }

    @Override
    public TokenDto register(RegisterForm form) {
        Optional<ErrorEntity> formErrorOrNull = validator.getUserRegisterFormError(form);
        if (formErrorOrNull.isPresent()) {
            throw new ValidationException(formErrorOrNull.get());
        }
        User user = User.builder()
                .fullName(form.getFullName())
                .email(form.getEmail())
                .password(form.getPassword())
                .roles(Collections.singleton(Role.ROLE_USER))
                .isActive(true)
                .isEmailConfirmed(false)
                .build();
        User savedUser = userRepository.save(user);
        return new TokenDto(jwtHelper.generateToken(savedUser));
    }

    @Override
    public ImageDto updateAvatar(User user, UploadImageDto uploadImageDto) {
        Optional<ErrorEntity> formErrorOrNull = validator.getImageFormDataError(uploadImageDto);
        if (formErrorOrNull.isPresent()) {
            throw new ValidationException(formErrorOrNull.get());
        }
        String imageName = imageFileService.save(uploadImageDto.getImage());
        Image image = imageRepository.save(new Image(imageName));
        user.setImage(image);
        userRepository.save(user);
        return imageMapper.toDtoConvert(image);
    }

    @Override
    public void deleteAvatar(User user) {
        Image image = user.getImage();
        if (image == null) {
            throw new NotFoundException("Image not found");
        }
        user.setImage(null);
        userRepository.save(user);
        imageRepository.delete(image);
        imageFileService.delete(image.getName());
    }
}
