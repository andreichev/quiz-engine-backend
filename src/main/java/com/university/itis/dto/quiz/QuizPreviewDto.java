package com.university.itis.dto.quiz;

import com.university.itis.dto.question.QuestionTextDto;
import com.university.itis.dto.quiz_passing.QuizPassingParticipantDto;
import com.university.itis.dto.user.UserShortDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizPreviewDto {
    private String id;
    private String title;
    private UserShortDto author;
    private String description;
    private Date startDate;
    private Boolean isAnyOrder;
    private Boolean isPublic;
    private List<QuestionTextDto> questions;
    private List<QuizPassingParticipantDto> results;
}
