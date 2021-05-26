package com.university.itis.dto.quiz;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditQuizForm {
    private Long id;
    private String title;
    private String description;
    private Boolean isAnyOrder;
    private Boolean isPublic;
}
