package com.university.itis.services;

import com.university.itis.dto.QuestionAnswerDto;
import com.university.itis.dto.QuizPassingDto;
import com.university.itis.model.User;

public interface QuizPassingService {
    QuizPassingDto createPassing(User user, String quizId);
    QuestionAnswerDto giveAnswer(User user, QuestionAnswerDto answerDto, Long passingId, String quizId);
}
