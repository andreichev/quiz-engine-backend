package com.university.itis.mapper;

import com.university.itis.dto.QuizPassingDto;
import com.university.itis.model.QuizPassing;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuizPassingMapper {
    private final UserMapper userMapper;
    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;
    private final QuestionAnswerMapper questionAnswerMapper;

    public QuizPassingDto toDtoConvert(QuizPassing quizPassing) {
        return QuizPassingDto.builder()
                .id(quizPassing.getId())
                .user(userMapper.toViewDto(quizPassing.getUser()))
                .quiz(quizMapper.toShortDtoConvert(quizPassing.getQuiz()))
                .questions(questionMapper.toListShortDtoConvert(quizPassing.getQuiz().getQuestions()))
                .answers(questionAnswerMapper.toListDtoConvert(quizPassing.getAnswers()))
                .build();
    }
}
