package com.university.itis.dto.quiz_passing;

import com.university.itis.dto.answer.QuestionAnswerDto;
import com.university.itis.dto.question.QuestionDto;
import com.university.itis.dto.quiz.QuizShortDto;
import com.university.itis.dto.user.UserShortDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FinishedQuizPassingDto {
    private Long id;
    private UserShortDto user;
    private QuizShortDto quiz;
    private List<QuestionDto> questions;
    private List<QuestionAnswerDto> answers;
    private Boolean isFinished;
    private Date startDate;
    private Float result;
}
