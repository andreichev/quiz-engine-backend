package com.university.itis.utils;

import com.university.itis.dto.LoginForm;
import com.university.itis.dto.QuizDto;
import com.university.itis.dto.RegisterForm;
import com.university.itis.model.User;
import com.university.itis.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class Validator extends ResponseCreator {
    private final int MIN_PASSWORD_LENGTH = 4;
    private final Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
    private final Pattern phonePattern = Pattern.compile("^[78]9\\d{9}$");

    private final UserService userService;

    public Optional<ErrorEntity> getLoginFormError(LoginForm form) {
        Optional<User> optionalUserEntity = userService.findOneByEmail(form.getEmail());
        if (optionalUserEntity.isPresent() == false) {
            return Optional.of(ErrorEntity.USER_NOT_FOUND);
        }
        User userEntity = optionalUserEntity.get();
        if (userEntity.getPassword().equals(form.getPassword()) == false) {
            return Optional.of(ErrorEntity.INCORRECT_PASSWORD);
        }
        return Optional.empty();
    }

    public Optional<ErrorEntity> getUserRegisterFormError(RegisterForm form) {
        if (form.getEmail() == null || emailPattern.matcher(form.getEmail()).matches() == false) {
            return Optional.of(ErrorEntity.INVALID_EMAIL);
        }
        if (form.getPhone() != null) {
            if (phonePattern.matcher(form.getPhone()).matches() == false) {
                return Optional.of(ErrorEntity.INVALID_PHONE);
            }
            if (userService.findOneByPhone(form.getPhone()).isPresent()) {
                return Optional.of(ErrorEntity.PHONE_ALREADY_TAKEN);
            }
        }
        if (form.getPassword() == null || form.getPassword().length() < MIN_PASSWORD_LENGTH) {
            return Optional.of(ErrorEntity.PASSWORD_TOO_SHORT);
        }
        if (userService.findOneByEmail(form.getEmail()).isPresent()) {
            return Optional.of(ErrorEntity.EMAIL_ALREADY_TAKEN);
        }
        return Optional.empty();
    }

    public Optional<ErrorEntity> getSaveQuizFormError(QuizDto form) {
        if (form.getTitle() == null) {
            return Optional.of(ErrorEntity.TITLE_REQUIRED);
        }
        if (form.getDescription() == null) {
            return Optional.of(ErrorEntity.DESCRIPTION_REQUIRED);
        }
        return Optional.empty();
    }
}
