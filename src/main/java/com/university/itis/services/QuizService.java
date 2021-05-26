package com.university.itis.services;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.model.User;

import java.util.List;

public interface QuizService {
    List<QuizShortDto> getAllPublic();
    List<QuizShortDto> getAllByAuthor(User user);
    QuizFullDto save(EditQuizForm form, User user);
    QuizFullDto getById(Long id, User user);
    QuizFullDto getBySecret(String secret);
    QuizFullDto update(Long id, EditQuizForm form, User user);
    void delete(Long id, User user);
}
