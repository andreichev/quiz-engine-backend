package com.university.itis.dto.quiz_passing;

import com.university.itis.dto.UserDto;
import com.university.itis.dto.answer.QuestionAnswerDto;
import com.university.itis.dto.question.QuestionShortDto;
import com.university.itis.dto.quiz.QuizShortDto;
import lombok.*;

import java.util.Date;
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
    private Boolean isFinished;
    private Date startDate;
}
