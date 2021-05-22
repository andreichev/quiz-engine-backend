package com.university.itis.services;

import com.university.itis.dto.UserDto;
import com.university.itis.model.User;

import java.util.Optional;

public interface UserService {
    UserDto save(User user);
    User getByEmail(String email);
    UserDto getByAuthToken(String authToken);
    Optional<User> findOneByEmail(String email);
    Optional<User> findOneByPhone(String phone);
    Optional<User> findOneById(Long id);
    boolean update(User user);
    boolean delete(User user);
}
