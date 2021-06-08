package com.university.itis.services;

import com.university.itis.dto.answer.QuestionAnswerForm;
import com.university.itis.dto.quiz.QuizParticipantsDto;
import com.university.itis.dto.quiz_passing.FinishedQuizPassingDto;
import com.university.itis.dto.quiz_passing.QuizPassingDto;
import com.university.itis.dto.quiz_passing.QuizPassingShortDto;
import com.university.itis.model.User;

import java.util.List;

public interface QuizPassingService {
    QuizPassingDto createPassing(User user, String quizId);
    void giveAnswer(User user, QuestionAnswerForm answerForm, Long passingId, String quizId);
    FinishedQuizPassingDto finishPassing(User user, String quizId, Long passingId);
    FinishedQuizPassingDto getFinishedQuizPassing(String quizId, Long passingId);
    QuizParticipantsDto getParticipants(Long userId, String quizId);
    List<QuizPassingShortDto> getHistory(User user);
}
