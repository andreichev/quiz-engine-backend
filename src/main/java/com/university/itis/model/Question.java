package com.university.itis.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@Setter
public class Question extends AbstractEntity implements Comparable<Question> {

    @Column
    private String text;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionAnswer> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionOption> options;

    @Override
    public int compareTo(Question o) {
        return (int) (this.getId() - o.getId());
    }
}
