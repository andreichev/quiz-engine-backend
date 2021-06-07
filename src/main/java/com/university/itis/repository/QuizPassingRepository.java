package com.university.itis.repository;

import com.university.itis.model.QuizPassing;
import com.university.itis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizPassingRepository extends JpaRepository<QuizPassing, Long> {
    Optional<QuizPassing> findByIdAndQuizId(Long id, String quizId);
    List<QuizPassing> getByUser(User user);
}
