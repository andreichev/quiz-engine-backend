package com.university.itis.dto.question;

import com.university.itis.dto.question_option.QuestionOptionShortDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionShortDto {
    private Long id;
    private String text;
    private List<QuestionOptionShortDto> options;
}