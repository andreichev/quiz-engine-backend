package com.university.itis.services;

import com.university.itis.dto.QuestionDto;
import com.university.itis.model.User;
import com.university.itis.utils.Result;

public interface QuestionService {
    Result getAllByQuizId(Long quizId);
    Result save(Long quizId, QuestionDto form, User user);
    Result getById(Long quizId, Long questionId);
    Result update(Long quizId, Long questionId, QuestionDto form, User user);
    Result delete(Long quizId, Long questionId, User user);
}
