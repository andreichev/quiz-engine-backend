package com.university.itis.model;

import javax.persistence.*;

@Entity
@Table(name = "question_option")
public class QuestionOption extends AbstractEntity implements Comparable<QuestionOption> {

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "correct")
    private boolean isCorrect;

    public QuestionOption() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public int compareTo(QuestionOption o) {
        return (int) (this.getId() - o.getId());
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
