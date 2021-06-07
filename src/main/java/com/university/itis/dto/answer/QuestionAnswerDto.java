package com.university.itis.dto.answer;

import com.university.itis.dto.question.QuestionTextDto;
import com.university.itis.dto.question_option.QuestionOptionDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerDto {
    private Long id;
    private QuestionTextDto question;
    private QuestionOptionDto option;
}
