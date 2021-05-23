package com.university.itis.repository;

import com.university.itis.model.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    Optional<QuestionOption> findByIsCorrectAndQuestionId(boolean isCorrect, Long questionId);
    List<QuestionOption> findAllByQuestionId(Long questionId);
}