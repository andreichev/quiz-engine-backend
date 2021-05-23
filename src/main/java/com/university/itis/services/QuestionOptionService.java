package com.university.itis.services;

import com.university.itis.dto.QuestionOptionDto;
import com.university.itis.model.User;
import com.university.itis.utils.Result;

public interface QuestionOptionService {
    Result getAllByQuestionId(Long questionId);
    Result save(Long questionId, QuestionOptionDto form, User user);
    Result getById(Long questionId, Long questionOptionId);
    Result update(Long questionId, Long questionOptionId, QuestionOptionDto form, User user);
    Result delete(Long questionId, Long questionOptionId, User user);
}
