package com.university.itis.services;

import com.university.itis.dto.QuestionDto;
import com.university.itis.model.User;

import java.util.List;

public interface QuestionService {
    QuestionDto saveQuestion(Long quizId, QuestionDto form, User user);
    List<QuestionDto> getAllByQuizId(Long quizId);
    QuestionDto updateQuestion(Long quizId, Long questionId, QuestionDto form, User user);
    QuestionDto getById(Long quizId, Long questionId);
    void delete(Long quizId, Long questionId, User user);
}
