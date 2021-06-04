package com.university.itis.services.impl;

import com.university.itis.dto.QuestionAnswerDto;
import com.university.itis.dto.QuizPassingDto;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.exceptions.ValidationException;
import com.university.itis.mapper.QuestionAnswerMapper;
import com.university.itis.mapper.QuizPassingMapper;
import com.university.itis.model.*;
import com.university.itis.repository.QuestionAnswerRepository;
import com.university.itis.repository.QuizPassingRepository;
import com.university.itis.repository.QuizRepository;
import com.university.itis.services.QuizPassingService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizPassingServiceImpl implements QuizPassingService {
    private final Validator validator;
    private final QuizRepository quizRepository;
    private final QuizPassingRepository quizPassingRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuestionAnswerMapper questionAnswerMapper;
    private final QuizPassingMapper quizPassingMapper;

    @Override
    public QuizPassingDto createPassing(User user, String quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + quizId + " not found"));
        QuizPassing quizPassingToSave = QuizPassing.builder()
                .quiz(quiz)
                .user(user)
                .answers(new ArrayList<>())
                .build();
        QuizPassing savedQuizPassing = quizPassingRepository.save(quizPassingToSave);
        return quizPassingMapper.toDtoConvert(savedQuizPassing);
    }

    @Override
    public QuestionAnswerDto giveAnswer(User user, QuestionAnswerDto answerDto, Long passingId, String quizId) {
        Optional<ErrorEntity> formErrorOrNull = validator.getQuestionAnswerError(answerDto);
        if (formErrorOrNull.isPresent()) {
            throw new ValidationException(formErrorOrNull.get());
        }
        Long questionId = answerDto.getQuestion().getId();
        Long optionId = answerDto.getOption().getId();

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + quizId + " not found"));
        QuizPassing quizPassing = quizPassingRepository.findById(passingId)
                .orElseThrow(() -> new NotFoundException("QuizPassing with id " + passingId + " not found"));
        Question question = quiz.getQuestions().stream()
                .filter(item -> item.getId().equals(questionId))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Question with id " + questionId + " not found"));
        QuestionOption option = question.getOptions().stream()
                .filter(item -> item.getId().equals(optionId))
                .findAny()
                .orElseThrow(() -> new NotFoundException("QuestionOption with id " + optionId + " not found"));
        Optional<QuestionAnswer> answerOptional = questionAnswerRepository.findByPassingIdAndQuestionId(passingId, questionId);
        QuestionAnswer answerToSave = answerOptional.orElseGet(() ->
                QuestionAnswer.builder()
                        .question(question)
                        .passing(quizPassing)
                    .build()
        );
        answerToSave.setOption(option);
        QuestionAnswer savedAnswer = questionAnswerRepository.save(answerToSave);
        return questionAnswerMapper.toDtoConvert(savedAnswer);
    }
}
