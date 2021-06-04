package com.university.itis.dto;

import com.university.itis.dto.question.QuestionShortDto;
import com.university.itis.dto.quiz.QuizShortDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizPassingDto {
    private Long id;
    private UserDto user;
    private QuizShortDto quiz;
    private List<QuestionShortDto> questions;
    private List<QuestionAnswerDto> answers;
}
