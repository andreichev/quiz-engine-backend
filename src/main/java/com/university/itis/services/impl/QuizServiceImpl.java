package com.university.itis.services.impl;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.exceptions.InvalidTokenException;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.mapper.QuizMapper;
import com.university.itis.model.Quiz;
import com.university.itis.model.User;
import com.university.itis.repository.QuizRepository;
import com.university.itis.services.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    @Override
    public List<QuizShortDto> getAllActive() {
        return quizMapper.toListDtoConvert(quizRepository.findAllByIsActiveIsTrue());
    }

    @Override
    public List<QuizShortDto> getAllByAuthor(User user) {
        return quizMapper.toListDtoConvert(quizRepository.findAllByAuthor(user));
    }

    @Override
    public QuizFullDto saveQuiz(EditQuizForm form) {
        Quiz quizToSave = quizMapper.toQuiz(form);
        Quiz savedQuiz = quizRepository.save(quizToSave);
        return quizMapper.toFullDtoConvert(savedQuiz);
    }

    @Override
    public QuizFullDto getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + id + " not found"));
        return quizMapper.toFullDtoConvert(quiz);
    }

    @Override
    public void deleteById(Long id, User user) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + id + " not found"));
        if (quiz.getAuthor().getId().equals(user.getId()) == false) {
            throw new InvalidTokenException("Доступ запрещен");
        }
        quizRepository.delete(quiz);
    }
}
