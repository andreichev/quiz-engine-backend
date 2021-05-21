package com.university.itis.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "question_option")
@Getter
@Setter
public class QuestionOption extends AbstractEntity implements Comparable<QuestionOption> {

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "correct")
    private boolean isCorrect;

    @Override
    public int compareTo(QuestionOption o) {
        return (int) (this.getId() - o.getId());
    }
}
