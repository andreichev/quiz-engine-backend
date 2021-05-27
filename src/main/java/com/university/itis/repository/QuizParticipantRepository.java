package com.university.itis.repository;

import com.university.itis.model.QuizPassing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizParticipantRepository extends JpaRepository<QuizPassing, Long> {
    List<QuizPassing> findAllByQuizId(String quizId);
}