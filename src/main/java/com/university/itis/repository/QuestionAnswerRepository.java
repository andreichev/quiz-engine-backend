package com.university.itis.repository;

import com.university.itis.model.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
    List<QuestionAnswer> findByParticipantId(Long participantId);
    Optional<QuestionAnswer> findByParticipantIdAndQuestionId(Long participantId, Long questionId);
}