package com.university.itis.services;

import com.university.itis.dto.QuizDto;
import com.university.itis.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizService {
    List<QuizDto> getAllActive();
    List<QuizDto> getAllByAuthor(User user);
    QuizDto saveQuiz(QuizDto quizDto);
    QuizDto getQuizById(Long id);
    void deleteById(Long id, User user);
}
