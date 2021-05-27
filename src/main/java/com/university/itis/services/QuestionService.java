package com.university.itis.services;

import com.university.itis.dto.QuestionDto;
import com.university.itis.model.User;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getAllByQuizId(String quizId);
    QuestionDto save(String quizId, QuestionDto form, User user);
    QuestionDto getById(String quizId, Long questionId);
    QuestionDto update(String quizId, Long questionId, QuestionDto form, User user);
    void delete(String quizId, Long questionId, User user);
}
