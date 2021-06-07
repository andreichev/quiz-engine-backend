package com.university.itis.dto.quiz_passing;

import com.university.itis.dto.quiz.QuizShortDto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizPassingShortDto {
    private Long id;
    private QuizShortDto quiz;
    private Date startDate;
    private Float result;
}
