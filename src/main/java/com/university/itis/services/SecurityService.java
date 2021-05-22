package com.university.itis.services;

import com.university.itis.model.User;

public interface SecurityService {
    User getByAuthToken(String token);
}
