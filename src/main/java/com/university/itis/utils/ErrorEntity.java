package com.university.itis.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorEntity {
    // Общие ошибки
    INVALID_REQUEST(400, "Неверный запрос"),
    INVALID_TOKEN(403, "Ошибка авторизации"),
    FORBIDDEN(403, "Доступ запрещен"),
    NOT_FOUND(404, "Не найдено"),
    INVALID_NAME(450, "Неверное имя"),
    // INVALID_PHONE(451, "Введите корректный телефон"),
    PHONE_ALREADY_TAKEN(452, "Телефон уже занят"),
    EMAIL_ALREADY_TAKEN(453, "Email уже занят"),
    TEXT_REQUIRED(450, "text - Обязательное поле"),
    TEXT_TOO_SHORT(460, "Текст слишком короткий, минимальная длина - " + Validator.MIN_TEXT_LENGTH),

    // Регистрация
    PASSWORD_TOO_SHORT(460, "Пароль слишком короткий, минимальная длина - " + Validator.MIN_PASSWORD_LENGTH),
    INVALID_EMAIL(461, "Некорректный Email"),
    INVALID_FULL_NAME(462, "Введите полное имя"),

    // Вход
    USER_NOT_FOUND(404,"Пользователь не найден"),
    INCORRECT_PASSWORD(460, "Неверный пароль"),

    // Quiz
    TITLE_REQUIRED(450, "title - Обязательное поле"),
    DESCRIPTION_REQUIRED(451, "description - Обязательное поле"),
    IS_PUBLIC_REQUIRED(452, "isPublic - Обязательное поле"),
    IS_ANY_ORDER_REQUIRED(453, "isAnyOrder - Обязательное поле"),

    // Question option
    IS_CORRECT_REQUIRED(451, "isCorrect - Обязательное поле"),

    // Выгрузка картинки
    ONLY_IMAGES_AVAILABLE_TO_UPLOAD(460, "Выгружать можно только картинки"),

    // Question answer
    QUESTION_OPTION_REQUIRED(450, "option с id - Обязательное поле"),
    QUESTION_REQUIRED(451, "question с id - Обязательное поле"),

    // Quiz passing
    QUIZ_PASSING_ALREADY_FINISHED(450, "Тест уже закончен"),
    QUIZ_PASSING_NOT_FINISHED(450, "Тест еще не закончен"),
    ;

    int status;
    String message;

    @JsonIgnore
    Logger log = LoggerFactory.logger(com.university.itis.utils.ErrorEntity.class);

    ErrorEntity(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public void log() {
        log.error("Ошибка " + status + ": " + message);
    }
}
