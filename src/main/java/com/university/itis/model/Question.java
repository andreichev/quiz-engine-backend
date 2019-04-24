package com.university.itis.model;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.List;
import java.util.SortedSet;

@Entity
@Table(name = "question")
public class Question extends AbstractEntity implements Comparable<Question> {

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionAnswer> questionAnswers;

    @SortNatural
    @OrderBy
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private SortedSet<QuestionOption> questionOptions;


    public Question() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public int compareTo(Question o) {
        return (int) (this.getId() - o.getId());
    }

    public SortedSet<QuestionOption> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(SortedSet<QuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
}
