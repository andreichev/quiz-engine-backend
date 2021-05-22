package com.university.itis.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDto {
    private Long id;
    private String title;
    private UserDto author;
    private String description;
    private Date startDate;
    private Boolean isAnyOrder;
    private Boolean isActive;
    private List<UserDto> participants;
}
