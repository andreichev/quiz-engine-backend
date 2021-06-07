package com.university.itis.dto.user;

import com.university.itis.dto.ImageDto;
import com.university.itis.model.Role;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private ImageDto avatar;
    private String fullName;
    private String email;
    private Set<Role> roles;
    private Boolean isActive;
    private Date registrationDate;
    private Integer quizzesCount;
    private Integer quizzesPassedCount;
}
