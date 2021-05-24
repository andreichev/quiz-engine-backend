package com.university.itis.services.impl;

import com.university.itis.dto.quiz.EditQuizForm;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.mapper.QuizMapper;
import com.university.itis.model.Quiz;
import com.university.itis.model.User;
import com.university.itis.repository.QuizRepository;
import com.university.itis.services.QuizService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.Result;
import com.university.itis.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final Validator validator;
    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    @Override
    public Result getAllActive() {
        return Result.success(quizMapper.toListDtoConvert(quizRepository.findAllByIsActiveIsTrue()));
    }

    @Override
    public Result getAllByAuthor(User user) {
        return Result.success(quizMapper.toListDtoConvert(quizRepository.findAllByAuthor(user)));
    }

    @Override
    public Result save(EditQuizForm form, User user) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuizFormError(form);
        if (formErrorOrNull.isPresent()) {
            return Result.error(formErrorOrNull.get());
        }
        Quiz quizToSave = quizMapper.toQuiz(form);
        quizToSave.setAuthor(user);
        quizToSave.setSecret(UUID.randomUUID().toString());
        Quiz savedQuiz = quizRepository.save(quizToSave);
        return Result.success(quizMapper.toFullDtoConvert(savedQuiz));
    }

    @Override
    public Result update(Long id, EditQuizForm form, User user) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuizFormError(form);
        if (formErrorOrNull.isPresent()) {
            return Result.error(formErrorOrNull.get());
        }
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + id + " not found"));
        Quiz quizToSave = quizMapper.toQuiz(form, quiz);
        Quiz savedQuiz = quizRepository.save(quizToSave);
        return Result.success(quizMapper.toFullDtoConvert(savedQuiz));
    }

    @Override
    public Result getById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + id + " not found"));
        return Result.success(quizMapper.toFullDtoConvert(quiz));
    }

    @Override
    public Result delete(Long id, User user) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + id + " not found"));
        if (quiz.getAuthor().getId().equals(user.getId()) == false) {
            return Result.error(ErrorEntity.FORBIDDEN);
        }
        quizRepository.delete(quiz);
        return Result.success();
    }
}
