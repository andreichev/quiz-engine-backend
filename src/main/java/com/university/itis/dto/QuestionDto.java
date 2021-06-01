package com.university.itis.dto;

import com.university.itis.dto.QuestionOptionDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private Long id;
    private String text;
    private List<QuestionOptionDto> options;
}
