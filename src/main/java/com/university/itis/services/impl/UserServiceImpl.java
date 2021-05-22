package com.university.itis.services.impl;

import com.university.itis.dto.UserDto;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.mapper.UserMapper;
import com.university.itis.model.User;
import com.university.itis.repository.UserRepository;
import com.university.itis.services.SecurityService;
import com.university.itis.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final SecurityService securityService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, SecurityService securityService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.securityService = securityService;
    }

    @Override
    public UserDto save(User user) {
        User savedUser = userRepository.save(user);
        return userMapper.toViewDto(savedUser);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }

    @Override
    public UserDto getByAuthToken(String token) {
        User user = securityService.getByAuthToken(token);
        return userMapper.toViewDto(user);
    }

    @Override
    public Optional<User> findOneById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findOneByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public boolean update(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
