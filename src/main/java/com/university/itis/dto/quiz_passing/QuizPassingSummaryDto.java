package com.university.itis.dto.quiz_passing;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizPassingSummaryDto {
    private Long id;
    private Integer questionsCount;
    private Integer correctAnswersCount;
    private Date startDate;
}
