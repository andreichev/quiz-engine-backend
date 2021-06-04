package com.university.itis.dto;

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
