package com.university.itis.services;

import com.university.itis.dto.QuestionDto;
import com.university.itis.model.User;

public interface QuestionService {
    QuestionDto saveQuestion(QuestionDto form, User user);
}
