package com.university.itis.dto.authorization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterForm {
    private String fullName;
    private String email;
    private String password;
}
