package com.university.itis.repository;

import com.university.itis.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuizId(String quizId);
    Optional<Question> findByIdAndQuizId(Long id, String quizId);
}