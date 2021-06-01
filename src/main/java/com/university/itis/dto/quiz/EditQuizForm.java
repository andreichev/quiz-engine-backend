package com.university.itis.dto.quiz;

import com.university.itis.dto.QuestionDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditQuizForm {
    private String title;
    private String description;
    private Boolean isAnyOrder;
    private Boolean isPublic;
    private List<QuestionDto> questions;
}
