package com.university.itis.dto.quiz_passing;

import com.university.itis.dto.user.UserShortDto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizPassingParticipantDto {
    private Long id;
    private UserShortDto user;
    private Date startDate;
}
