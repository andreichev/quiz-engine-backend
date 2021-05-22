package com.university.itis.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionOptionDto {
    private String text;
    private Boolean isCorrect;
}
