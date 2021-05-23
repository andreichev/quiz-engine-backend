package com.university.itis.services;

import com.university.itis.dto.QuestionDto;
import com.university.itis.model.User;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getAllByQuizId(Long quizId);
    QuestionDto save(Long quizId, QuestionDto form, User user);
    QuestionDto getById(Long quizId, Long questionId);
    QuestionDto update(Long quizId, Long questionId, QuestionDto form, User user);
    void delete(Long quizId, Long questionId, User user);
}
