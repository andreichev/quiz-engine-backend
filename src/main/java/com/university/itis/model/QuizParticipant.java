package com.university.itis.model;

import javax.persistence.*;

@Entity
@Table(name = "quiz_participant")
public class QuizParticipant extends AbstractEntity {

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
