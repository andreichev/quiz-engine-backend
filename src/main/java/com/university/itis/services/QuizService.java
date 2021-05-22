package com.university.itis.services;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.model.User;

import java.util.List;

public interface QuizService {
    List<QuizShortDto> getAllActive();
    List<QuizShortDto> getAllByAuthor(User user);
    QuizFullDto saveQuiz(EditQuizForm form);
    QuizFullDto updateQuiz(Long id, EditQuizForm form);
    QuizFullDto getQuizById(Long id);
    void deleteById(Long id, User user);
}
