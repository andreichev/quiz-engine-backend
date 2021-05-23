package com.university.itis.services;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.model.User;
import com.university.itis.utils.Result;

public interface QuizService {
    Result getAllActive();
    Result getAllByAuthor(User user);
    Result save(EditQuizForm form, User user);
    Result getById(Long id);
    Result update(Long id, EditQuizForm form, User user);
    Result delete(Long id, User user);
}
