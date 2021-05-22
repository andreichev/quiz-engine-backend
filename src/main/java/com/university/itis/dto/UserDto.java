package com.university.itis.dto;

import com.university.itis.model.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private Set<Role> roles;
    private String phone;
    private Boolean isActive;
}
