package com.university.itis.dto.quiz;

import com.university.itis.dto.UserDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditQuizForm {
    private Long id;
    private String title;
    private UserDto author;
    private String description;
    private Boolean isAnyOrder;
    private Boolean isActive;
}
