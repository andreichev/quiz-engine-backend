package com.university.itis.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private Long id;
    private Long quizId;
    private String text;
    private List<QuestionOptionDto> options;
}
