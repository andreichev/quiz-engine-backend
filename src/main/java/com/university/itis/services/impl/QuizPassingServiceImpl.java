package com.university.itis.services.impl;

import com.university.itis.dto.answer.QuestionAnswerForm;
import com.university.itis.dto.quiz.QuizParticipantsDto;
import com.university.itis.dto.quiz_passing.FinishedQuizPassingDto;
import com.university.itis.dto.quiz_passing.QuizPassingDto;
import com.university.itis.dto.quiz_passing.QuizPassingShortDto;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.exceptions.ValidationException;
import com.university.itis.mapper.QuizParticipantsMapper;
import com.university.itis.mapper.QuizPassingMapper;
import com.university.itis.model.*;
import com.university.itis.repository.QuestionAnswerRepository;
import com.university.itis.repository.QuizPassingRepository;
import com.university.itis.repository.QuizRepository;
import com.university.itis.repository.UserRepository;
import com.university.itis.services.QuizPassingService;
import com.university.itis.utils.ErrorEntity;
import com.university.itis.utils.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizPassingServiceImpl implements QuizPassingService {
    private final Validator validator;
    private final QuizRepository quizRepository;
    private final QuizPassingRepository quizPassingRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final QuizPassingMapper quizPassingMapper;
    private final QuizParticipantsMapper quizParticipantsMapper;
    private final UserRepository userRepository;

    @Override
    public QuizPassingDto createPassing(User user, String quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + quizId + " not found"));
        QuizPassing quizPassingToSave = QuizPassing.builder()
                .quiz(quiz)
                .user(user)
                .answers(new ArrayList<>())
                .startDate(new Date())
                .isFinished(false)
                .build();
        QuizPassing savedQuizPassing = quizPassingRepository.save(quizPassingToSave);
        return quizPassingMapper.toDtoConvert(savedQuizPassing);
    }

    @Override
    public void giveAnswer(User user, QuestionAnswerForm answerForm, Long passingId, String quizId) {
        Optional<ErrorEntity> formErrorOrNull = validator.getQuestionAnswerError(answerForm);
        if (formErrorOrNull.isPresent()) {
            throw new ValidationException(formErrorOrNull.get());
        }
        Long questionId = answerForm.getQuestion().getId();
        Long optionId = answerForm.getOption().getId();

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
        QuestionAnswer answer = answerOptional.orElseGet(() ->
                QuestionAnswer.builder()
                        .question(question)
                        .passing(quizPassing)
                    .build()
        );
        answer.setOption(option);
        questionAnswerRepository.save(answer);
    }

    @Override
    public FinishedQuizPassingDto finishPassing(User user, String quizId, Long passingId) {
        QuizPassing quizPassing = quizPassingRepository.findByIdAndQuizId(passingId, quizId)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + quizId + "QuizPassing with id " + passingId + " not found"));
        if(quizPassing.getIsFinished()) {
            throw new ValidationException(ErrorEntity.QUIZ_PASSING_ALREADY_FINISHED);
        }
        quizPassing.setIsFinished(true);
        quizPassing = quizPassingRepository.save(quizPassing);
        return quizPassingMapper.toFinishedDtoConvert(quizPassing);
    }

    @Override
    public FinishedQuizPassingDto getFinishedQuizPassing(String quizId, Long passingId) {
        QuizPassing quizPassing = quizPassingRepository.findByIdAndQuizId(passingId, quizId)
                .orElseThrow(() -> new NotFoundException("Quiz with id " + quizId + "QuizPassing with id " + passingId + " not found"));
        if(quizPassing.getIsFinished() == false) {
            throw new ValidationException(ErrorEntity.QUIZ_PASSING_NOT_FINISHED);
        }
        return quizPassingMapper.toFinishedDtoConvert(quizPassing);
    }

    @Override
    public List<QuizPassingShortDto> getHistory(User user) {
        return quizPassingMapper.toListShortDtoConvert(quizPassingRepository.getByUser(user));
    }

    @Override
    public QuizParticipantsDto getParticipants(Long userId, String quizId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        List<QuizPassing> results = quizPassingRepository.findAllByUserIdAndQuizId(userId, quizId);
        return quizParticipantsMapper.toDtoConvert(user, results);
    }
}
