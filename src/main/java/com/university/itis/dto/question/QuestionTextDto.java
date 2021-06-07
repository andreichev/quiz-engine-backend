package com.university.itis.dto.question;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionTextDto {
    private Long id;
    private String text;
}
