package com.university.itis.services.impl;

import com.university.itis.config.filter.JwtHelper;
import com.university.itis.exceptions.InvalidTokenException;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.model.User;
import com.university.itis.repository.UserRepository;
import com.university.itis.services.SecurityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;

    public SecurityServiceImpl(JwtHelper jwtHelper, UserRepository userRepository) {
        this.jwtHelper = jwtHelper;
        this.userRepository = userRepository;
    }

    @Override
    public User getByAuthToken(String token) {
        if (token == null || jwtHelper.validateToken(token) == false) {
            throw new InvalidTokenException("Invalid token or token header not found");
        }
        String email = jwtHelper.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            String tokenPassword = jwtHelper.getPasswordFromToken(token);
            if(tokenPassword.equals(user.getPassword()) == false) {
                throw new InvalidTokenException("Wrong password");
            }
            return optionalUser.get();
        } else {
            throw new NotFoundException("User with email " + email + " not found");
        }
    }
}
