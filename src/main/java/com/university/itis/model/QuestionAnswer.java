package com.university.itis.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question_answer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private QuestionOption option;

    @ManyToOne
    @JoinColumn(name = "passing_id", nullable = false)
    private QuizPassing passing;
}
