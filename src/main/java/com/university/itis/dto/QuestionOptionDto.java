package com.university.itis.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionOptionDto {
    private Long id;
    private String text;
    private Boolean isCorrect;
}
