package com.university.itis.dto;

import com.university.itis.dto.question.QuestionDto;
import com.university.itis.dto.question_option.QuestionOptionDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerDto {
    private Long id;
    private QuestionDto question;
    private QuestionOptionDto option;
}
