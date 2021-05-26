package com.university.itis.utils;

import com.university.itis.dto.LoginForm;
import com.university.itis.dto.QuestionDto;
import com.university.itis.dto.QuestionOptionDto;
import com.university.itis.dto.RegisterForm;
import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.model.User;
import com.university.itis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class Validator {
    public static final int MIN_PASSWORD_LENGTH = 4;
    public static final int MIN_TEXT_LENGTH = 1;
    private final Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
    private final Pattern fullNamePattern = Pattern.compile("^[a-zA-Z ,.'-]+$");

    private final UserRepository userRepository;

    public Optional<ErrorEntity> getLoginFormError(LoginForm form) {
        Optional<User> optionalUserEntity = userRepository.findByEmail(form.getEmail());
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
        if (form.getFullName() == null || fullNamePattern.matcher(form.getFullName()).matches() == false) {
            return Optional.of(ErrorEntity.INVALID_FULL_NAME);
        }
        if (form.getEmail() == null || emailPattern.matcher(form.getEmail()).matches() == false) {
            return Optional.of(ErrorEntity.INVALID_EMAIL);
        }
        if (form.getPassword() == null || form.getPassword().length() < MIN_PASSWORD_LENGTH) {
            return Optional.of(ErrorEntity.PASSWORD_TOO_SHORT);
        }
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            return Optional.of(ErrorEntity.EMAIL_ALREADY_TAKEN);
        }
        return Optional.empty();
    }

    public Optional<ErrorEntity> getSaveQuizFormError(EditQuizForm form) {
        if (form.getTitle() == null) {
            return Optional.of(ErrorEntity.TITLE_REQUIRED);
        }
        if (form.getDescription() == null) {
            return Optional.of(ErrorEntity.DESCRIPTION_REQUIRED);
        }
        if(form.getIsPublic() == null) {
            return Optional.of(ErrorEntity.IS_PUBLIC_REQUIRED);
        }
        if(form.getIsAnyOrder() == null) {
            return Optional.of(ErrorEntity.IS_ANY_ORDER_REQUIRED);
        }
        return Optional.empty();
    }

    public Optional<ErrorEntity> getSaveQuestionFormError(QuestionDto form) {
        if (form.getText() == null) {
            return Optional.of(ErrorEntity.TEXT_REQUIRED);
        }
        if (form.getText().length() < MIN_TEXT_LENGTH) {
            return Optional.of(ErrorEntity.TEXT_TOO_SHORT);
        }
        return Optional.empty();
    }

    public Optional<ErrorEntity> getSaveQuestionOptionFormError(QuestionOptionDto form) {
        if (form.getText() == null) {
            return Optional.of(ErrorEntity.TEXT_REQUIRED);
        }
        if (form.getText().length() < MIN_TEXT_LENGTH) {
            return Optional.of(ErrorEntity.TEXT_TOO_SHORT);
        }
        if (form.getIsCorrect() == null) {
            return Optional.of(ErrorEntity.IS_CORRECT_REQUIRED);
        }
        return Optional.empty();
    }
}
