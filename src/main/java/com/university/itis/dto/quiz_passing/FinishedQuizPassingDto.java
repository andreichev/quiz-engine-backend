package com.university.itis.dto.quiz_passing;

import com.university.itis.dto.QuestionAnswerDto;
import com.university.itis.dto.UserDto;
import com.university.itis.dto.question.QuestionDto;
import com.university.itis.dto.quiz.QuizShortDto;
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
    private UserDto user;
    private QuizShortDto quiz;
    private List<QuestionDto> questions;
    private List<QuestionAnswerDto> answers;
    private Boolean isFinished;
    private Date startDate;
}
