package com.university.itis.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "question_option")
@Getter
@Setter
public class QuestionOption extends AbstractEntity {

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "correct", nullable = false)
    private Boolean isCorrect;
}
