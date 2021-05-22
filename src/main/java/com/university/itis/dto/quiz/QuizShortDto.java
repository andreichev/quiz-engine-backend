package com.university.itis.dto.quiz;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizShortDto {
    private Long id;
    private String title;
    private String description;
}
