package com.university.itis.services.impl;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.dto.quiz.QuizFullDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.exceptions.InvalidTokenException;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.exceptions.ValidationException;
import com.university.itis.mapper.QuizMapper;
import com.university.itis.model.Quiz;
import com.university.itis.model.User;
import com.university.itis.repository.QuizRepository;
import com.university.itis.services.QuizService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final Validator validator;
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    @Override
    public List<QuizShortDto> getAllPublic() {
        return quizMapper.toListDtoConvert(quizRepository.findAllByIsPublicIsTrue());
    }

    @Override
    public List<QuizShortDto> getAllByAuthor(User user) {
        return quizMapper.toListDtoConvert(quizRepository.findAllByAuthor(user));
    }

    @Override
    public QuizFullDto save(EditQuizForm form, User user) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuizFormError(form);
        if (formErrorOrNull.isPresent()) {
            throw new ValidationException(formErrorOrNull.get());
        }
        Quiz quizToSave = quizMapper.toQuiz(form);
        quizToSave.setParticipants(Collections.emptyList());
        quizToSave.setQuestions(Collections.emptyList());
        quizToSave.setStartDate(new Date());
        quizToSave.setAuthor(user);
        quizToSave.setSecret(UUID.randomUUID().toString());
        Quiz savedQuiz = quizRepository.save(quizToSave);
        return quizMapper.toFullDtoConvert(savedQuiz);
    }

    @Override
    public QuizFullDto update(Long id, EditQuizForm form, User user) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuizFormError(form);
        if (formErrorOrNull.isPresent()) {
            throw new ValidationException(formErrorOrNull.get());
        }
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + id + " not found"));
        Quiz quizToSave = quizMapper.toQuiz(form, quiz);
        Quiz savedQuiz = quizRepository.save(quizToSave);
        return quizMapper.toFullDtoConvert(savedQuiz);
    }

    @Override
    public QuizFullDto getById(Long id, User user) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + id + " not found"));
        if(quiz.isPublic() == false && quiz.getAuthor().getId().equals(user.getId()) == false) {
            throw new InvalidTokenException("Доступ запрещен.");
        }
        return quizMapper.toFullDtoConvert(quiz);
    }

    @Override
    public QuizFullDto getBySecret(String secret) {
        Quiz quiz = quizRepository.findBySecret(secret)
                .orElseThrow(() -> new NotFoundException("Quiz with secret " + secret + " not found"));
        return quizMapper.toFullDtoConvert(quiz);
    }

    @Override
    public void delete(Long id, User user) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + id + " not found"));
        if (quiz.getAuthor().getId().equals(user.getId()) == false) {
            throw new InvalidTokenException("Доступ запрещен");
        }
        quizRepository.delete(quiz);
    }
}
