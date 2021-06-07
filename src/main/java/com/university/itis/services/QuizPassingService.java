package com.university.itis.services;

import com.university.itis.dto.QuestionAnswerDto;
import com.university.itis.dto.quiz_passing.FinishedQuizPassingDto;
import com.university.itis.dto.quiz_passing.QuizPassingDto;
import com.university.itis.dto.quiz_passing.QuizPassingShortDto;
import com.university.itis.model.User;

import java.util.List;

public interface QuizPassingService {
    QuizPassingDto createPassing(User user, String quizId);
    QuestionAnswerDto giveAnswer(User user, QuestionAnswerDto answerDto, Long passingId, String quizId);
    FinishedQuizPassingDto finishPassing(User user, String quizId, Long passingId);
    FinishedQuizPassingDto getFinishedQuizPassing(String quizId, Long passingId);
    List<QuizPassingShortDto> getHistory(User user);
}
