package com.university.itis.dto.quiz;

import com.university.itis.dto.QuestionDto;
import com.university.itis.dto.UserDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizFullDto {
    private Long id;
    private String title;
    private UserDto author;
    private String description;
    private Date startDate;
    private Boolean isAnyOrder;
    private Boolean isPublic;
    private List<QuestionDto> questions;
    private List<UserDto> participants;
}
