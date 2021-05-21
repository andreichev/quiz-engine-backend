package com.university.itis.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question_answer")
@Getter
@Setter
public class QuestionAnswer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private QuestionOption questionOption;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private QuizParticipant participant;
}
