package com.university.itis.repository;

import com.university.itis.model.QuizParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizParticipantRepository extends JpaRepository<QuizParticipant, Long> {
}