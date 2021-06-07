package com.university.itis.dto.answer;

import com.university.itis.dto.question.QuestionDto;
import com.university.itis.dto.question_option.QuestionOptionDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerForm {
    private QuestionDto question;
    private QuestionOptionDto option;
}