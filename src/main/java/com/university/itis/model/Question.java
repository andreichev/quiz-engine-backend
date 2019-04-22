package com.university.itis.model;

import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.util.SortedSet;

@Entity
@Table(name = "question")
public class Question extends AbstractEntity implements Comparable<Question> {

    @Column
    private String text;

    @Column(name = "order_num")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    @Override
    public int compareTo(Question o) {
        final int BEFORE = -1;
        final int AFTER = 1;
        if (o == null) {
            return BEFORE;
        }
        Comparable<Integer> thisQuestionOptionOrder = this.getOrder();
        Comparable<Integer> oQuestionOptionOrder = o.getOrder();
        if (thisQuestionOptionOrder == null) {
            return AFTER;
        } else if (oQuestionOptionOrder == null) {
            return BEFORE;
        } else {
            return thisQuestionOptionOrder.compareTo(o.getOrder());
        }
    }

    public SortedSet<QuestionOption> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(SortedSet<QuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
    }
}
