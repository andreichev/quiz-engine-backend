package com.university.itis.repository;

import com.university.itis.model.Quiz;
import com.university.itis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
    List<Quiz> findAllByIsPublicIsTrue();
    List<Quiz> findAllByAuthor(User author);
}