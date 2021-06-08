package com.university.itis.dto.quiz;

import com.university.itis.dto.quiz_passing.QuizPassingSummaryDto;
import com.university.itis.dto.user.UserShortDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizParticipantsDto {
    private UserShortDto user;
    private List<QuizPassingSummaryDto> results;
}