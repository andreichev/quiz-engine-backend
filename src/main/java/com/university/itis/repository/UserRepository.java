package com.university.itis.repository;

import com.university.itis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFullName(String fullName);
    Optional<User> findByEmail(String email);
}