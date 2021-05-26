package com.university.itis.services.impl;

import com.university.itis.dto.QuestionOptionDto;
import com.university.itis.exceptions.InvalidTokenException;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.exceptions.ValidationException;
import com.university.itis.mapper.QuestionOptionMapper;
import com.university.itis.model.Question;
import com.university.itis.model.QuestionOption;
import com.university.itis.model.Quiz;
import com.university.itis.model.User;
import com.university.itis.repository.QuestionOptionRepository;
import com.university.itis.repository.QuestionRepository;
import com.university.itis.services.QuestionOptionService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionOptionServiceImpl implements QuestionOptionService {
    private final Validator validator;
    private final QuestionOptionRepository questionOptionRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionMapper questionOptionMapper;

    @Override
    public List<QuestionOptionDto> getAllByQuestionId(Long questionId) {
        return questionOptionMapper
                .toListDtoConvert(questionOptionRepository.findAllByQuestionId(questionId));
    }

    @Override
    public QuestionOptionDto save(Long questionId, QuestionOptionDto form, User user) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuestionOptionFormError(form);
        if (formErrorOrNull.isPresent()) {
            throw new ValidationException(formErrorOrNull.get());
        }
        QuestionOption questionOptionToSave = questionOptionMapper.toQuestionOption(form);
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id " + questionId + " not found"));
        Quiz quiz = question.getQuiz();
        if (quiz.getAuthor().getId().equals(user.getId()) == false) {
            throw new InvalidTokenException("Доступ запрещен");
        }
        questionOptionToSave.setQuestion(question);
        QuestionOption savedQuestionOption = questionOptionRepository.save(questionOptionToSave);
        return questionOptionMapper.toDtoConvert(savedQuestionOption);
    }

    @Override
    public QuestionOptionDto update(Long questionId, Long questionOptionId, QuestionOptionDto form, User user) {
        Optional<ErrorEntity> formErrorOrNull = validator.getSaveQuestionOptionFormError(form);
        if (formErrorOrNull.isPresent()) {
            throw new ValidationException(formErrorOrNull.get());
        }
        QuestionOption questionOption = getQuestionOption(questionId, questionOptionId, user);
        QuestionOption questionOptionToSave = questionOptionMapper.toQuestionOption(form, questionOption);
        QuestionOption savedQuestionOption = questionOptionRepository.save(questionOptionToSave);
        return questionOptionMapper.toDtoConvert(savedQuestionOption);
    }

    @Override
    public QuestionOptionDto getById(Long questionId, Long questionOptionId) {
        QuestionOption questionOption = questionOptionRepository.findByIdAndQuestionId(questionOptionId, questionId)
                .orElseThrow(() -> new NotFoundException("Question with id " + questionId + " or question option with id " + questionOptionId + " not found"));
        return questionOptionMapper.toDtoConvert(questionOption);
    }

    @Override
    public void delete(Long questionId, Long questionOptionId, User user) {
        questionOptionRepository.delete(getQuestionOption(questionId, questionOptionId, user));
    }

    private QuestionOption getQuestionOption(Long questionId, Long questionOptionId, User user) {
        QuestionOption questionOption = questionOptionRepository.findById(questionOptionId)
                .orElseThrow(() -> new NotFoundException("Question option with id " + questionOptionId + " not found"));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NotFoundException("Question with id " + questionId + " not found"));
        if (question.getQuiz().getAuthor().getId().equals(user.getId()) == false) {
            throw new InvalidTokenException("Доступ запрещен");
        }
        return questionOption;
    }
}
