package com.university.itis.services;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizPreviewDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.model.User;

import java.util.List;

public interface QuizService {
    List<QuizShortDto> getAllPublic();
    List<QuizShortDto> getAllByAuthor(User user);
    QuizPreviewDto save(EditQuizForm form, User user);
    QuizPreviewDto getById(String id);
    QuizFullDto getFullById(User user, String id);
    QuizPreviewDto update(String id, EditQuizForm form, User user);
    void delete(String id, User user);
}
