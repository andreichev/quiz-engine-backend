package com.university.itis.dto.user;

import com.university.itis.dto.ImageDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserShortDto {
    private Long id;
    private ImageDto avatar;
    private String fullName;
    private String email;
}
